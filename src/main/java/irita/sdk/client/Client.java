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
        String addr = split[0];
        int port = Integer.parseInt(split[1]);
        return ManagedChannelBuilder.forAddress(addr, port).usePlaintext().build();
    }

    @Override
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, boolean offline) throws IritaSDKException, CryptoException, IOException {
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

        byte[] signature = SM2Utils.sign(privKey, signDoc.toByteArray());
        BigInteger[] rs = SM2Utils.getRSFromSignature(signature);
        byte[] sigBytes = addAll(toBytesPadded(rs[0], 32), toBytesPadded(rs[1], 32));

        return TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();
    }


    // TODO move this to correct position
    public static byte[] addAll(byte[] array1, byte... array2) {
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;

    }

    // TODO move this to correct position
    public static byte[] toBytesPadded(BigInteger value, int length) {
        byte[] result = new byte[length];
        byte[] bytes = value.toByteArray();
        int bytesLength;
        byte srcOffset;
        if (bytes[0] == 0) {
            bytesLength = bytes.length - 1;
            srcOffset = 1;
        } else {
            bytesLength = bytes.length;
            srcOffset = 0;
        }

        if (bytesLength > length) {
            throw new RuntimeException("Input is too large to put in byte array of size " + length);
        } else {
            int destOffset = length - bytesLength;
            System.arraycopy(bytes, srcOffset, result, destOffset, bytesLength);
            return result;
        }
    }

    public Account queryAccount(String addr) {
        ManagedChannel channel = getGrpcClient();
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest.newBuilder().setAddress(addr).build();
        QueryOuterClass.QueryAccountResponse res = QueryGrpc.newBlockingStub(channel).account(req);

        Auth.BaseAccount baseAccount = null;
        try {
            baseAccount = res.getAccount().unpack(Auth.BaseAccount.class);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        if (baseAccount != null) {
            account.setAddress(baseAccount.getAddress());
            account.setAccountNumber(baseAccount.getAccountNumber());
            account.setSequence(baseAccount.getSequence());
        }
        return account;
    }
}
