package irita.sdk.module.keys;

import java.math.BigInteger;

public interface Key {
    byte[] sign(String stdSignMsg);

    BigInteger getPrivKey();

    String getAddr();
}


