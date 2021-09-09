package irita.sdk.config;

public class TxConfig {
    private final int gas;
    private final String memo;
    private final ModeEnum Mode;
//    boolean Simulate;
//    double gasAdujstment;

    public TxConfig(int gas, String memo, ModeEnum mode) {
        this.gas = gas;
        this.memo = memo;
        Mode = mode;
    }

    public int getGas() {
        return gas;
    }

    public String getMemo() {
        return memo;
    }

    public ModeEnum getMode() {
        return Mode;
    }
}
