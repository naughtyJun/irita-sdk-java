package irita.sdk.key;

import java.io.InputStream;
import java.math.BigInteger;

/**
 * Secp256k1KeyManger will implement in the future
 */
public class Secp256k1KeyManger extends KeyManager {
    @Override
    public void add() {
        throw new RuntimeException("TODO");
    }

    @Override
    public void recover(String mnemonic) {
        throw new RuntimeException("TODO");
    }

    @Override
    public void recover(BigInteger privKey) {
        throw new RuntimeException("TODO");
    }

    @Override
    public void recover(InputStream keystore, String password) {
        throw new RuntimeException("TODO");
    }

    @Override
    public void recoverFromCA(InputStream caKeystore, String password) {
        throw new RuntimeException("TODO");
    }

    @Override
    public String export(String password) {
        throw new RuntimeException("TODO");
    }
}
