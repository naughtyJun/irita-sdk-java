package irita.sdk.key;

import java.math.BigInteger;

public abstract class KeyManager implements Key {
    private BigInteger privKey;
    private String addr;
    private String mnemonic;
    // this keyPath and hrp just for iris
    private String keyPath = "m/44'/118'/0'/0/0";
    private String hrp = "iaa";

    public BigInteger getPrivKey() {
        return privKey;
    }

    protected void setPrivKey(BigInteger privKey) {
        this.privKey = privKey;
    }

    public String getAddr() {
        return addr;
    }

    protected void setAddr(String addr) {
        this.addr = addr;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    protected void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public String getHrp() {
        return hrp;
    }

    public void setHrp(String hrp) {
        this.hrp = hrp;
    }
}
