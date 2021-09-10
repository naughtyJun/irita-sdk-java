package irita.sdk.client;

import irita.sdk.module.bank.BankClient;
import irita.sdk.module.community_gov.CommunityGovClient;
import irita.sdk.module.wasm.WasmClient;
import irita.sdk.new_client.BaseClient;

public class IritaClient extends Client {
    private BaseClient baseClient;
    private BankClient bankClient;
    private WasmClient wasmClient;
    private CommunityGovClient communityGovClient;


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
        if (this.bankClient == null) {
            this.bankClient = new BankClient(this.nodeUri, this.grpcAddr, this.chainId, this.option);
        }

        return this.bankClient;
    }

    public BaseClient getBaseClient() {
        if (this.baseClient == null) {
            this.baseClient = new BaseClient();
        }

        return this.baseClient;
    }

    public WasmClient getWasmClient() {
        if (this.wasmClient == null) {
            this.wasmClient = new WasmClient(this.nodeUri, this.grpcAddr, this.chainId, this.option);
        }

        return this.wasmClient;
    }

    public CommunityGovClient getCommunityGovClient() {
        if (this.communityGovClient == null) {
            this.communityGovClient = new CommunityGovClient(this.getWasmClient());
        }

        return this.communityGovClient;
    }
}
