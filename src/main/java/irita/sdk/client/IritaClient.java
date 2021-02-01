package irita.sdk.client;

import irita.sdk.module.bank.BankClient;
import irita.sdk.module.base.BaseClient;
import irita.sdk.module.wasm.WasmClient;

public class IritaClient extends Client {
    private BaseClient baseClient;
    private BankClient bankClient;
    private WasmClient wasmClient;


    public IritaClient() {
    }

    public IritaClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    public String getNodeUri() {
        return nodeUri;
    }

    public void setNodeUri(String nodeUri) {
        this.nodeUri = nodeUri;
    }

    public String getGrpcAddr() {
        return grpcAddr;
    }

    public void setGrpcAddr(String grpcAddr) {
        this.grpcAddr = grpcAddr;
    }

    public String getChainId() {
        return chainId;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public IritaClientOption getOption() {
        return option;
    }

    public void setOption(IritaClientOption option) {
        this.option = option;
    }

    public BankClient getBankClient() {
        if (this.bankClient != null) {
            return this.bankClient;
        } else {
            return new BankClient(this.nodeUri, this.grpcAddr, this.chainId, this.option);
        }
    }

    public BaseClient getBaseClient() {
        if (this.baseClient != null) {
            return this.baseClient;
        } else {
            return new BaseClient(this.nodeUri, this.grpcAddr, this.chainId, this.option);
        }
    }

    public WasmClient getWasmClient() {
        if (this.wasmClient != null) {
            return this.wasmClient;
        } else {
            return new WasmClient(this.nodeUri, this.grpcAddr, this.chainId, this.option);
        }
    }
}
