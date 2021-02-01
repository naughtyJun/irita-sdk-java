package irita.sdk.module.base;

import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;

public class BaseClient extends Client {
    private IritaClientOption option;

    public BaseClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    public Account queryAccount(String addr) {
        return super.queryAccount(addr);
    }

    public IritaClientOption getOption() {
        return option;
    }

    public void setOption(IritaClientOption option) {
        this.option = option;
    }
}
