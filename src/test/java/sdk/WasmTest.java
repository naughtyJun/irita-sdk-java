package sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.module.community_gov.CommunityGovClient;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import irita.sdk.module.wasm.StoreRequest;
import irita.sdk.module.wasm.WasmClient;
import org.bouncycastle.crypto.CryptoException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class WasmTest {
    private IritaClient client;
    private WasmClient wasmClient;

    @Before
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String grpcAddr = "http://localhost:9090";
        String chainId = "irita";
        client = new IritaClient(nodeUri, grpcAddr, chainId, option);
        wasmClient = client.getWasmClient();

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
    }

    // when you want to add new Contract to block-chain, remove the @Ignore
    @Ignore
    @Test
    public void TestWasm() throws CryptoException, IOException {
        StoreRequest req = new StoreRequest();
        req.setWasmFile("src/main/resources/election.wasm");
        String codId = wasmClient.store(req);
        assertNotNull(codId);
        System.out.println(codId);
    }

}
