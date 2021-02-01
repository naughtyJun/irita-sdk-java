package irita.sdk.module.wasm;

import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;

public class WasmClient extends Client {
    public WasmClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }
}
