package irita.sdk;

import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyManagerTest {
    @Test
    public void recoverFromMnemonic() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
        assertEquals("3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec", km.getPrivKey().toString(16));
    }

    @Test
    public void recoverFromPrivateKey() {
        BigInteger privKey = new BigInteger("3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec", 16);
        Key km = new KeyManager(privKey);

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
        assertEquals("3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec", km.getPrivKey().toString(16));
    }

    @Test
    public void export() throws IOException {
        BigInteger privKey = new BigInteger("3c49175daf981965679bf88d2690e22144424e16c84e9d397ddb58b63603eeec", 16);
        Key km = new KeyManager(privKey);

        String keystore = km.export("123456");
        System.out.println(keystore);
    }
}
