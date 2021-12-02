package irita.sdk.tx;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyInfo;
import irita.sdk.key.KeyManager;
import irita.sdk.model.Account;
import irita.sdk.model.BaseTx;
import irita.sdk.util.SecP256K1Utils;
import org.bouncycastle.math.ec.ECPoint;
import proto.cosmos.base.v1beta1.CoinOuterClass;
import proto.cosmos.crypto.secp256k1.Keys;
import proto.cosmos.tx.signing.v1beta1.Signing;
import proto.cosmos.tx.v1beta1.TxOuterClass;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Secp256k1TxEngine implements TxEngine {
    private final KeyManager km;
    private final String chainID;

    public Secp256k1TxEngine(KeyManager km, String chainID) {
        this.km = km;
        this.chainID = chainID;
    }

    @Override
    public TxOuterClass.TxBody buildTxBody(List<GeneratedMessageV3> msgs) {
        return this.buildTxBodyWithMemo(msgs, null);
    }

    @Override
    public TxOuterClass.TxBody buildTxBodyWithMemo(List<GeneratedMessageV3> msgs, String memo) {
        if (msgs.size() == 0) {
            throw new IritaSDKException("size of msgs should larger than 0");
        }
        TxOuterClass.TxBody.Builder builder = TxOuterClass.TxBody.newBuilder();
        msgs.forEach(msg -> {
            builder.addMessages(Any.pack(msg, "/"));
        });
        return builder
                .setMemo(Optional.ofNullable(memo).orElse(""))
                .setTimeoutHeight(0)
                .build();
    }

    @Override
    public TxOuterClass.Tx signTx(TxOuterClass.TxBody txBody, BaseTx baseTx, Account account) {
        Objects.requireNonNull(baseTx, "baseTx not be null");

        KeyInfo keyInfo = km.getKeyDAO().read(baseTx.getFrom(), baseTx.getPassword());
        BigInteger privKey = keyInfo.getPrivKey();
        ECPoint publicKey = keyInfo.getPublicKey();
        byte[] publicKeyEncoded = publicKey.getEncoded(true);

        TxOuterClass.AuthInfo ai = TxOuterClass.AuthInfo.newBuilder()
                .addSignerInfos(
                        TxOuterClass.SignerInfo.newBuilder()
                                .setPublicKey(Any.pack(Keys.PubKey.newBuilder().setKey(ByteString.copyFrom(publicKeyEncoded)).build(), "/"))
                                .setModeInfo(TxOuterClass.ModeInfo.newBuilder().setSingle(TxOuterClass.ModeInfo.Single.newBuilder().setMode(Signing.SignMode.SIGN_MODE_DIRECT)))
                                .setSequence(account.getSequence()))
                .setFee(TxOuterClass.Fee.newBuilder().setGasLimit(baseTx.getGas()).addAmount(CoinOuterClass.Coin.newBuilder().setAmount(baseTx.getFee().getAmount()).setDenom(baseTx.getFee().getDenom()))).build();


        TxOuterClass.SignDoc signDoc = TxOuterClass.SignDoc.newBuilder()
                .setBodyBytes(txBody.toByteString())
                .setAuthInfoBytes(ai.toByteString())
                .setAccountNumber(account.getAccountNumber())
                .setChainId(chainID)
                .build();

        byte[] sigBytes = SecP256K1Utils.sign(privKey, signDoc.toByteArray());

        return TxOuterClass.Tx.newBuilder()
                .setBody(txBody)
                .setAuthInfo(ai)
                .addSignatures(ByteString.copyFrom(sigBytes))
                .build();
    }
}
