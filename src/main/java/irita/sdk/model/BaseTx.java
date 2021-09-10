package irita.sdk.model;

import irita.sdk.client.IritaClientOption;
import irita.sdk.constant.enums.BroadcastMode;

public class BaseTx {
    private int gas;
    private IritaClientOption.Fee fee;
    private String memo;
    private BroadcastMode mode;

    public BaseTx() {
    }

    public BaseTx(int gas, IritaClientOption.Fee fee) {
        this.gas = gas;
        this.fee = fee;
    }

    public int getGas() {
        return gas;
    }

    public BaseTx setGas(int gas) {
        this.gas = gas;
        return this;
    }

    public IritaClientOption.Fee getFee() {
        return fee;
    }

    public BaseTx setFee(IritaClientOption.Fee fee) {
        this.fee = fee;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public BaseTx setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public BroadcastMode getMode() {
        return mode;
    }

    public BaseTx setMode(BroadcastMode mode) {
        this.mode = mode;
        return this;
    }
}
