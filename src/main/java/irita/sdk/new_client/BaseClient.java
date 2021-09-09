package irita.sdk.new_client;

import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import irita.sdk.config.ClientConfig;
import irita.sdk.config.OpbConfig;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.base.Account;
import irita.sdk.tx.TxEngine;
import proto.cosmos.auth.v1beta1.Auth;
import proto.cosmos.auth.v1beta1.QueryGrpc;
import proto.cosmos.auth.v1beta1.QueryOuterClass;

public class BaseClient {
    private ClientConfig clientConfig;
    private OpbConfig opbConfig;
    private TxEngine txEngine;

    private ManagedChannel grpcClient;


    public BaseClient() {
    }

    public BaseClient(ClientConfig clientConfig, OpbConfig opbConfig, TxEngine txEngine) {
        this.clientConfig = clientConfig;
        this.opbConfig = opbConfig;
        this.txEngine = txEngine;

        this.grpcClient = Conn.createGrpcClient(clientConfig, opbConfig);
    }

    // TODO next
    public void getRpcClient() {
    }

    protected ManagedChannel getGrpcClient() {
        return grpcClient;
    }

    public Account queryAccount(String address) {
        ManagedChannel channel = getGrpcClient();
        QueryOuterClass.QueryAccountRequest req = QueryOuterClass.QueryAccountRequest
                .newBuilder()
                .setAddress(address)
                .build();

        QueryOuterClass.QueryAccountResponse resp = QueryGrpc.newBlockingStub(channel).account(req);
        channel.shutdown();

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

    public void brodcast() {
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
