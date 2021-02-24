package irita.sdk;

import com.alibaba.fastjson.JSONObject;
import irita.opb.OpbOption;
import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.constant.ContractAddress;
import irita.sdk.constant.enums.Role;
import irita.sdk.exception.ContractException;
import irita.sdk.module.bank.BankClient;
import irita.sdk.module.base.Account;
import irita.sdk.module.base.BaseTx;
import irita.sdk.module.base.ResultTx;
import irita.sdk.module.community_gov.CommunityGovClient;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import irita.sdk.module.wasm.InstantiateRequest;
import irita.sdk.module.wasm.StoreRequest;
import irita.sdk.module.wasm.WasmClient;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OpbTest {
    private IritaClient client;
    private BankClient bankClient;
    private WasmClient wasmClient;
    private CommunityGovClient comGovClient;

    @BeforeEach
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);

        IritaClientOption.Fee fee = new IritaClientOption.Fee("130", "irita");
        fee.toMin();

        IritaClientOption option = new IritaClientOption(10, fee, 1073741824, "", 1.0, km);

        OpbOption opbOption = new OpbOption("https://opbningxia.bsngate.com:18602", "7b3c53beda5c48c6b07d98804e156389", null);
        String chainId = "wenchangchain";

        client = new IritaClient(chainId, opbOption, option);
        bankClient = client.getBankClient();
        wasmClient = client.getWasmClient();
        comGovClient = client.getCommunityGovClient();
    }

    @Test
    public void queryAccountNumber() {
        Account account = client.queryAccount("iaa1wfs050mv8taydn4cttsrhr5dq3tpdaemcm5sk2");
        System.out.println(account.getAccountNumber());
        assertTrue(account.getAccountNumber() > 0);
    }

    @Test
    @Disabled
    public void send() throws IOException {
        String res = bankClient.send("1", "iaa1wfs050mv8taydn4cttsrhr5dq3tpdaemcm5sk2");
        ResultTx resultTx = JSONObject.parseObject(res, ResultTx.class);
        System.out.println(resultTx);
    }

    @Test
    public void newContract() throws IOException {
        // store contract
        StoreRequest storeReq = new StoreRequest();
        storeReq.setWasmFile("src/test/resources/community_governance.wasm");
        BaseTx baseTx = new BaseTx(2000000, new IritaClientOption.Fee("20000000", "uirita"));

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

    @Test
    public void addMember() {
        String newAddr = "iaa1wfs050mv8taydn4cttsrhr5dq3tpdaemcm5sk2";

        try {
            comGovClient.addMember(newAddr, Role.HASH_ADMIN);
        } catch (ContractException | IOException e) {
            e.printStackTrace();
        }

        // verify from db
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);
        assertNotNull(map.get(newAddr));
    }

    @Test
    public void exportContractState() {
        Map<String, String> map = wasmClient.exportContractState(ContractAddress.DEFAULT);
        System.out.println(map);
    }
}
