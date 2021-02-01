package sdk;

import irita.sdk.client.IritaClient;
import irita.sdk.client.IritaClientOption;
import irita.sdk.module.bank.BankClient;
import irita.sdk.module.base.Account;
import irita.sdk.module.base.BaseClient;
import irita.sdk.module.keys.Key;
import irita.sdk.module.keys.KeyManager;
import org.bouncycastle.crypto.CryptoException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class ClientTest {
    private IritaClient client;

    @Before
    public void init() {
        String mnemonic = "opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);

        String nodeUri = "http://localhost:26657";
        String grpcAddr = "http://localhost:9090";
        String chainId = "irita";
        client = new IritaClient(nodeUri, grpcAddr, chainId, option);

        assertEquals("iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3",km.getAddr());
    }

    @Test
    public void newClient() {
        BaseClient baseClient = client.getBaseClient();
        String addr = "iaa1ytemz2xqq2s73ut3ys8mcd6zca2564a5lfhtm3";
        Account account = baseClient.queryAccount(addr);
        assertEquals(addr, account.getAddress());
    }

    @Test
    public void send() throws CryptoException, IOException {
        BankClient bankClient = client.getBankClient();
        bankClient.send("");
    }
}
