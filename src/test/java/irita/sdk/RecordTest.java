package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.constant.enums.BroadcastMode;
import irita.sdk.key.AlgoEnum;
import irita.sdk.key.KeyManager;
import irita.sdk.key.KeyManagerFactory;
import irita.sdk.model.BaseTx;
import irita.sdk.model.Fee;
import irita.sdk.module.record.CreateRecordResult;
import irita.sdk.module.record.RecordClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import proto.record.QueryOuterClass;
import proto.record.RecordOuterClass;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecordTest {
    private KeyManager km;
    private RecordClient recordClient;
    private IritaClient client;
    private BaseTx baseTx = new BaseTx(200000, new Fee("300000", "uirita"), BroadcastMode.Commit);

    @BeforeEach
    public void init() {
        Properties properties = Config.getTestConfig();
        String mnemonic = properties.getProperty("mnemonic");
        km = KeyManagerFactory.createDefault();
        km.recover(mnemonic);

        String nodeUri = properties.getProperty("node_uri");
        String grpcAddr = properties.getProperty("grpc_addr");
        String chainId = properties.getProperty("chain_id");
        ClientConfig clientConfig = new ClientConfig(nodeUri, grpcAddr, chainId);
        OpbConfig opbConfig = null;

        client = new IritaClient(clientConfig, opbConfig, km);
        recordClient = client.getRecordClient();
        assertEquals(properties.getProperty("address"), km.getCurrentKeyInfo().getAddress());
    }

    @Test
    @Disabled
    public void testCreateRecord() throws IOException {
        List<RecordOuterClass.Content> contents = Collections.singletonList(RecordOuterClass.Content.newBuilder()
                .setDigest("test record digest")
                .setDigestAlgo("test record digest algo")
                .setUri("https://test.record.com")
                .setMeta("test metadata")
                .build());
        CreateRecordResult createRecordResult = recordClient.createRecord(contents, baseTx);
        System.out.println(createRecordResult.getRecordID());
        assertNotNull(createRecordResult);
    }

    @Test
    @Disabled
    public void testQueryRecord() {
        QueryOuterClass.QueryRecordResponse recordResponse = recordClient.queryRecord("676aff876e56a711757bcbaf69d17ad57d539f6267fc448f435394d8c4af5d4a");
        assertNotNull(recordResponse);
    }
}
