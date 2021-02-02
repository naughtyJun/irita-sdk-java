package irita.sdk.module.keys;

import irita.sdk.util.Bech32Utils;
import irita.sdk.util.Bip44Utils;
import irita.sdk.util.HashUtils;
import irita.sdk.util.SM2Utils;
import org.bitcoinj.crypto.DeterministicKey;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class KeyManager implements Key {
    private BigInteger privKey;
    private String addr;
    private String mnemonic;
    // this KEY_PATH just for iris
    private static final String KEY_PATH = "m/44'/118'/0'/0/0";
    private static final String HRP = "iaa";

    public KeyManager(String mnemonic) {
        byte[] seed = Bip44Utils.getSeed(mnemonic);
        DeterministicKey dk = Bip44Utils.getDeterministicKey(mnemonic, seed, KEY_PATH);
        BigInteger privKey = dk.getPrivKey();
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);

        byte[] encoded = publicKey.getEncoded(true);
        byte[] hash = HashUtils.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);

        this.addr = Bech32Utils.toBech32(HRP, pre20);
        this.privKey = privKey;
        this.mnemonic = mnemonic;
    }

    public KeyManager(BigInteger privKey) {
        ECPoint publicKey = SM2Utils.getPublicKeyFromPrivkey(privKey);
        byte[] encoded = publicKey.getEncoded(true);
        byte[] hash = HashUtils.sha256(encoded);
        byte[] pre20 = new byte[20];
        System.arraycopy(hash, 0, pre20, 0, 20);

        this.addr = Bech32Utils.toBech32(HRP, pre20);
        this.privKey = privKey;
    }

    @Override
    public BigInteger getPrivKey() {
        return privKey;
    }

    @Override
    public String getAddr() {
        return addr;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    @Override
    public byte[] sign(String stdSignMsg) {
        byte[] signDoc = stdSignMsg.getBytes(StandardCharsets.UTF_8);
        byte[] res = new byte[0];
        try {
            res = SM2Utils.sign(this.getPrivKey(), signDoc);
        } catch (CryptoException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public String toString() {
        return "KeyManager{" +
                "privKey='" + privKey + '\'' +
                ", addr='" + addr + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                '}';
    }
}

