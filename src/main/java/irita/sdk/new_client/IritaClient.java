package irita.sdk.new_client;

public class IritaClient {
    private NftClient nftClient;
    private BankClient bankClient;

    public IritaClient() {
    }


    public IritaClient(NftClient nftClient, BankClient bankClient) {
        this.nftClient = nftClient;
        this.bankClient = bankClient;
    }

    public NftClient getNftClient() {
        return nftClient;
    }

    public BankClient getBankClient() {
        return bankClient;
    }
}
