package irita.sdk.tx;

import irita.sdk.key.AlgoEnum;
import irita.sdk.key.KeyManager;

public class TxEngineFactory {
    private TxEngineFactory() {
    }

    public static TxEngine createTxEngine(AlgoEnum algo, KeyManager km) {
        switch (algo) {
            case SM2:
                return new Sm2TxEngine(km);
            case SECP256K1:
                return new Secp256k1TxEngine();
            default:
                throw new RuntimeException("panic");
        }
    }

    public static TxEngine createDefault(KeyManager km) {
        return new Sm2TxEngine(km);
    }
}
