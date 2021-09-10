package irita.sdk.client;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.key.KeyManager;
import irita.sdk.model.Account;
import irita.sdk.model.BaseTx;
import irita.sdk.model.ResultTx;
import irita.sdk.tx.TxEngine;
import irita.sdk.tx.TxEngineFactory;
import proto.cosmos.auth.v1beta1.Auth;
import proto.cosmos.auth.v1beta1.QueryGrpc;
import proto.cosmos.auth.v1beta1.QueryOuterClass;

import java.io.IOException;

public class BaseClient {
    private ClientConfig clientConfig;
    private OpbConfig opbConfig;
    private KeyManager km;

    private TxEngine txEngine;
    private ManagedChannel grpcClient;
    private RpcClient rpcClient;

    public BaseClient() {
    }

    public BaseClient(ClientConfig clientConfig, OpbConfig opbConfig, KeyManager keyManager) {
        this.clientConfig = clientConfig;
        this.opbConfig = opbConfig;
        this.km = keyManager;

        this.txEngine = TxEngineFactory.createDefault(km, clientConfig.getChainID());
        this.grpcClient = GrpcFactory.createGrpcClient(clientConfig, opbConfig);
        this.rpcClient = new RpcClient(clientConfig, opbConfig);
    }

    public String getCurrentAddr() {
        return km.getAddr();
    }

    public RpcClient getRpcClient() {
        return rpcClient;
    }

    public ManagedChannel getGrpcClient() {
        return grpcClient;
    }

    public ResultTx buildAndSend(com.google.protobuf.GeneratedMessageV3 msg, BaseTx baseTx) throws IOException {
        return buildAndSend(msg, baseTx, null);
    }

    public ResultTx buildAndSend(com.google.protobuf.GeneratedMessageV3 msg, BaseTx baseTx, Account account) throws IOException {
        if (account == null) {
            account = queryAccount();
        }
        TxEngine txEngine = getTxEngine();
        byte[] txBytes = txEngine.buildAndSign(msg, baseTx, account);
        return rpcClient.broadcastTx(txBytes, baseTx.getMode());
    }

    public Account queryAccount() {
        return queryAccount(km.getAddr());
    }

    public Account queryAccount(String address) {
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest
                .newBuilder()
                .setAddress(address)
                .build();
        QueryOuterClass.QueryAccountResponse resp = QueryGrpc.newBlockingStub(grpcClient).account(req);

        Auth.BaseAccount baseAccount = null;
        try {
            baseAccount = resp.getAccount().unpack(Auth.BaseAccount.class);
        } catch (InvalidProtocolBufferException e) {
            throw new IritaSDKException("account:\t" + address + "is not exist", e);
        }

        Account account = new Account();
        account.setAddress(baseAccount.getAddress());
        account.setAccountNumber(baseAccount.getAccountNumber());
        account.setSequence(baseAccount.getSequence());
        return account;
    }

    public void subscribe() {
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public OpbConfig getOpbConfig() {
        return opbConfig;
    }

    public void setOpbConfig(OpbConfig opbConfig) {
        this.opbConfig = opbConfig;
    }

    public TxEngine getTxEngine() {
        return txEngine;
    }

    public void setTxEngine(TxEngine txEngine) {
        this.txEngine = txEngine;
    }
}
