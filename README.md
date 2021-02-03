# irita-sdk-java

irita-sdk-java

## How to use irita-sdk-java

### 1 use in normal java project

```java
        String mnemonic="opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best";
        String nodeUri=http://localhost:26657;
        Key km=new KeyManager(mnemonic);
        IritaClientOption option=IritaClientOption.getDefaultOption(km);
        IritaClient client=new IritaClient(nodeUri,grpcAddr,chainId,option);
        WasmClient wasmClient=iritaClient.getWasmClient();
        CommunityGovClient comGovClient=iritaClient.getCommunityGovClient();
        // get other client is as same as above
```

### 2 use at spring

Write next in application.yml

```yaml
irita:
  sdk:
    mnemonic: opera vivid pride shallow brick crew found resist decade neck expect apple chalk belt sick author know try tank detail tree impact hand best
    nodeUri: http://localhost:26657
    grpcAddr: http://localhost:9090
    chainId: irita
```

**Then use @ConfigurationProperties + @Component to register Bean.**
You can also use EnableConfigurationProperties(IritaSdkConfig.class) as you like

```java

@Configuration
@Data
@ConfigurationProperties(prefix = "irita.sdk")
public class IritaSdkConfig {
    private String mnemonic;
    private String nodeUri;
    private String grpcAddr;
    private String chainId;

    @Bean
    public IritaClient iritaClient() {
        Key km = new KeyManager(mnemonic);
        IritaClientOption option = IritaClientOption.getDefaultOption(km);
        return new IritaClient(nodeUri, grpcAddr, chainId, option);
    }

    @Bean
    public WasmClient wasmClient(IritaClient iritaClient) {
        return iritaClient.getWasmClient();
    }

    @Bean
    public CommunityGovClient comGovClient(IritaClient iritaClient) {
        return iritaClient.getCommunityGovClient();
    }
}
```
