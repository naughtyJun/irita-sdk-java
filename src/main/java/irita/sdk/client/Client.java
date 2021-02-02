package irita.sdk.client;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import cosmos.auth.v1beta1.Auth;
import cosmos.auth.v1beta1.QueryGrpc;
import cosmos.auth.v1beta1.QueryOuterClass;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.crypto.sm2.Keys;
import cosmos.tx.signing.v1beta1.Signing;
import cosmos.tx.v1beta1.TxOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.base.Account;
import irita.sdk.module.base.TxService;
import irita.sdk.module.keys.Key;
import irita.sdk.util.ByteUtils;
import irita.sdk.util.SM2Utils;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.math.ec.ECPoint;

import java.io.IOException;
import java.math.BigInteger;

public abstract class Client implements TxService {
    protected IritaClientOption option;
    protected String nodeUri;
    protected String grpcAddr;
    protected String chainId;

    protected ManagedChannel getGrpcClient() {
        String httpProtocol = "http://";
        if (grpcAddr.startsWith(httpProtocol)) {
            grpcAddr = grpcAddr.substring(httpProtocol.length());
        }

        String[] split = grpcAddr.split(":");
        if (split.length != 2) {
            throw new IritaSDKException("grpcAddr:\t" + grpcAddr + "is not correct");
        }

        String addr = split[0];
        int port = Integer.parseInt(split[1]);
        return ManagedChannelBuilder.forAddress(addr, port).usePlaintext().build();
    }

    @Override
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, boolean offline) {
        Key km = option.getKeyManager();
        BigInteger privKey = km.getPrivKey();
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);
        byte[] publicKeyEncoded = publicKey.getEncoded(true);

        Account account = queryAccount(km.getAddr());
        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Any.pack(Keys.PubKey.newBuilder().setKey(ByteString.copyFrom(publicKeyEncoded)).build(), "/"))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(account.getSequence()))
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(option.getGasLimit()).addAmount(CoinOuterClass.Coin.newBuilder().setAmount(option.getFee().amount).setDenom(option.getFee().denom))).build();


        TxOuterClass.SignDoc signDoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(account.getAccountNumber())
                .setChainId(chainId)
                .build();

        byte[] signature;
        BigInteger[] rs;
        try {
            signature = SM2Utils.sign(privKey, signDoc.toByteArray());
            rs = SM2Utils.getRSFromSignature(signature);
        } catch (CryptoException | IOException e) {
            throw new IritaSDKException("use sm2 sign filed", e);
        }
        byte[] sigBytes = ByteUtils.addAll(ByteUtils.toBytesPadded(rs[0], 32), ByteUtils.toBytesPadded(rs[1], 32));

        return TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();
    }

    // if you want to add memo, you will build by yourSelf
    public TxOuterClass.TxBody buildTxBody(com.google.protobuf.GeneratedMessageV3 msg) {
        return TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msg, "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();
    }

    public Account queryAccount(String address) {
        ManagedChannel channel = getGrpcClient();
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest
                .newBuilder()
                .setAddress(address)
                .build();

        QueryOuterClass.QueryAccountResponse resp = QueryGrpc.newBlockingStub(channel).account(req);

        Auth.BaseAccount baseAccount = null;
        try {
            baseAccount = resp.getAccount().unpack(Auth.BaseAccount.class);
        } catch (InvalidProtocolBufferException e) {
            throw new IritaSDKException("account:\t" + address + "is not exist", e);
        }

        Account account = new Account();
        account.setAddress(baseAccount.getAddress());
        account.setAccountNumber(baseAccount.getAccountNumber());
        account.setSequence(baseAccount.getSequence());
        return account;
    }
}
