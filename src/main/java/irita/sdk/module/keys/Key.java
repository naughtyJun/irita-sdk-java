package irita.sdk.module.keys;

import java.io.IOException;
import java.math.BigInteger;

public interface Key {
    byte[] sign(String stdSignMsg);

    BigInteger getPrivKey();

    String getAddr();

    String export(String password) throws IOException;
}


