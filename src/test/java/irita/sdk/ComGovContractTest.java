package irita.sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.constant.ContractAddress;
import irita.sdk.constant.enums.RoleEnum;
import irita.sdk.exception.ContractException;
import irita.sdk.module.base.BaseTx;
import irita.sdk.module.community_gov.CommunityGovClient;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import irita.sdk.module.wasm.InstantiateRequest;
import irita.sdk.module.wasm.StoreRequest;
import irita.sdk.module.wasm.WasmClient;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Ignore
public class ComGovContractTest {
    private CommunityGovClient comGovClient;
    private WasmClient wasmClient;

    @Before
    @Ignore
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String grpcAddr = "http://localhost:9090";
        String chainId = "irita";
        IritaClient client = new IritaClient(nodeUri, grpcAddr, chainId, option);
        wasmClient = client.getWasmClient();
        comGovClient = client.getCommunityGovClient();

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3", km.getAddr());
    }

    // it is ignore unless you want to new community_governance contract to block-chain.
    @Test
    public void newContract() throws IOException {
        // store contract
        StoreRequest storeReq = new StoreRequest();
        storeReq.setWasmFile("src/main/resources/community_governance.wasm");
        BaseTx baseTx = new BaseTx(2000000, new IritaClientOption.Fee("120", "stake"));

        String codeId = wasmClient.store(storeReq, baseTx);
        assertTrue(StringUtils.isNotEmpty(codeId));

        // init contract
        InstantiateRequest initReq = new InstantiateRequest();
        initReq.setLabel("test-com-gov");
        initReq.setCodeId(Long.parseLong(codeId));
        initReq.setInitMsg(new HashMap<>());
        String contractorAddr = wasmClient.instantiate(initReq, baseTx);

        assertTrue(StringUtils.isNotEmpty(contractorAddr));

        System.out.println(codeId);
        System.out.println(contractorAddr);
    }

    @Test
    public void addDepartment() {
        // publicKey == address in this version
        final String publicKey = "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3";
        final String department = "测试部门";

        try {
            comGovClient.addDepartment(department, publicKey);
        } catch (ContractException e) {
            // you can use log to record
            e.printStackTrace();
        }

        // query contract for valid
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);

        String v1 = map.get(department);
        assertEquals("{}", v1);
        String v2 = map.get("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3");
        assertEquals("{\"department_name\":\"测试部门\",\"role\":0,\"public_key\":\"iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3\"}", v2);
    }

    // first update will success
    // second will false, because already transfer the department
    @Test
    public void updateDepartment() {
        // publicKey == address in this version
        String publicKey = "otherPerson";
        try {
            comGovClient.updateDepartment(publicKey);
        } catch (ContractException e) {
            assertTrue(e.getMessage().contains("community_governance::state::Identity"));
        }
    }

    @Test
    public void addMember() {
        String newAddr = "iaa1ufs8gvg6qf6nzpx7wme5tmyja34dva58kqtx2z";

        try {
            comGovClient.addMember(newAddr, RoleEnum.ADMIN);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        // verify from db
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);
        assertNotNull(map.get(newAddr));
    }

    @Test
    public void removeMember() {
        String removeAddr = "iaa1ufs8gvg6qf6nzpx7wme5tmyja34dva58kqtx2z";

        try {
            comGovClient.removeMember(removeAddr);
        } catch (ContractException e) {
            e.printStackTrace();
        }

        // verify from db
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);
        assertNull(map.get(removeAddr));
    }

    @Test
    public void addHash() {
        // new client, he role of cur_address must hash_admin
        String mnemonic = "apart various produce pond bachelor size pumpkin gate pretty awake silver worth dust pledge pioneer patrol current fall escape lunar zero afraid this fish";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String grpcAddr = "http://localhost:9090";
        String chainId = "irita";
        CommunityGovClient comGovClient1 = new IritaClient(nodeUri, grpcAddr, chainId, option).getCommunityGovClient();

        // exec add_hash
        String docType = "1";
        String docId = "1";
        String strHash = "789";
        String fileHash = null;

        try {
            comGovClient1.addHash(docType, docId, strHash, fileHash);
        } catch (IOException | ContractException e) {
            e.printStackTrace();
        }

        // verify from db
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);
        System.out.println(map);
        assertNotNull(map.get(docType + docId));
        assertNotNull(map.get(strHash));
    }

    @Test
    public void getHash() {
        String strHash = "789";
        String fileHash = "789";
        boolean isExisted = false;

        try {
            isExisted = comGovClient.getHash(strHash, fileHash);
        } catch (ContractException e) {
            e.printStackTrace();
        }
        assertTrue(isExisted);
    }

    // this is a pressure test, it is ignore by default
    // Repeat execution add_member and remove_addr
    @Test
    @Ignore
    public void pressureTest() {
        String addr = "iaa1ufs8gvg6qf6nzpx7wme5tmyja34dva58kqtx2z";

        int i = 0;
        while (i < 100) {
            try {
                comGovClient.addMember(addr, RoleEnum.ADMIN);
                comGovClient.removeMember(addr);
            } catch (ContractException e) {
                e.printStackTrace();
            }
            i++;
        }

    }
}
