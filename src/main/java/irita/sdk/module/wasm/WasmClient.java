package irita.sdk.module.wasm;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import cosmos.wasm.QueryGrpc;
import cosmos.wasm.QueryOuterClass;
import cosmos.wasm.Tx;
import cosmos.wasm.Wasm;
import io.grpc.ManagedChannel;
import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;
import irita.sdk.constant.enums.EventEnum;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.base.*;
import irita.sdk.util.HttpUtils;
import irita.sdk.util.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WasmClient extends Client {
    public WasmClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    // upload the contract to block-chain and return the codeId for user
    public String store(StoreRequest req) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());

        if (req.getWasmByteCode() != null) {
            req.setWasmByteCode(req.getWasmByteCode());
        } else {
            byte[] bytes = IOUtils.readAll(req.getWasmFile());
            if (bytes == null) {
                // TODO fix this
                throw new IritaSDKException("file not open");
            }
            req.setWasmByteCode(bytes);
        }

        Tx.MsgStoreCode msg = Tx.MsgStoreCode.newBuilder()
                .setSender(account.getAddress())
                .setWasmByteCode(ByteString.copyFrom(req.getWasmByteCode()))
                .setSource("")
                .setBuilder("")
                .setInstantiatePermission(Wasm.AccessConfig.newBuilder().build())
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(body, false);
        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        ResultTx resultTx = JSON.parseObject(res, ResultTx.class);

        if ("0".equals(resultTx.getResult().getHeight())) {
            throw new IritaSDKException(resultTx.getResult().getCheck_tx().getLog());
        }

        return Optional.of(resultTx)
                .map(ResultTx::getResult)
                .map(Result::getDeliver_tx)
                .map(x -> x.getEventValue(EventEnum.MessageCodeId))
                .orElse(null);
    }

    // instantiate the contract state
    public String instantiate(InstantiateRequest req) throws  IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgInstantiateContract msg = Tx.MsgInstantiateContract.newBuilder()
                .setSender(account.getAddress())
                .setAdmin(req.getAdmin())
                .setCodeId(req.getCodeId())
                .setInitMsg(
                        ByteString.copyFrom(
                                JSON.toJSONString(req.getInitMsg()).getBytes(StandardCharsets.UTF_8)))
                .addInitFunds((
                        CoinOuterClass.Coin.newBuilder()
                                .setAmount(req.getInitFunds().getAmount())
                                .setDenom(req.getInitFunds().getDenom())
                                .build()))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(body, false);
        return HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
    }

    // execute the contract method
    public Result execute(String contractAddress, ContractABI abi, Coin sentFunds) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        byte[] msgBytes = abi.build();

        Tx.MsgExecuteContract msg = Tx.MsgExecuteContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .addSentFunds(
                        CoinOuterClass.Coin.newBuilder()
                                .setAmount(sentFunds.getAmount())
                                .setDenom(sentFunds.getDenom())
                                .build())
                .setMsg(ByteString.copyFrom(msgBytes))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(body, false);
        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        return JSON.parseObject(res, Result.class);
    }

    public Result migrate(String contractAddress, long newCodeID, byte[] msgByte) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgMigrateContract msg = Tx.MsgMigrateContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .setCodeId(newCodeID)
                .setMigrateMsg(ByteString.copyFrom(msgByte))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(body, false);
        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        return JSON.parseObject(res, Result.class);
    }

    // return the contract information
    public ContractInfo queryContractInfo(String address) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryContractInfoRequest req = QueryOuterClass.QueryContractInfoRequest
                .newBuilder()
                .setAddress(address)
                .build();

        QueryOuterClass.QueryContractInfoResponse resp = QueryGrpc.newBlockingStub(channel).contractInfo(req);
        return Convert.toContractInfo(resp);
    }

    // execute contract's query method and return the result
    public byte[] queryContract(String address, ContractABI abi) {
        ManagedChannel channel = super.getGrpcClient();

        byte[] queryData = JSON.toJSONBytes(abi);
        QueryOuterClass.QuerySmartContractStateRequest req = QueryOuterClass.QuerySmartContractStateRequest
                .newBuilder()
                .setAddress(address)
                .setQueryData(ByteString.copyFrom(queryData))
                .build();

        QueryOuterClass.QuerySmartContractStateResponse resp = QueryGrpc.newBlockingStub(channel).smartContractState(req);
        return resp.toByteArray();
    }

    // export all state data of the contract
    public Map<String, byte[]> exportContractState(String address) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryAllContractStateRequest req = QueryOuterClass.QueryAllContractStateRequest
                .newBuilder()
                .setAddress(address)
                .build();

        QueryOuterClass.QueryAllContractStateResponse resp = QueryGrpc.newBlockingStub(channel).allContractState(req);

        List<Wasm.Model> models = resp.getModelsList();
        return models.stream().collect(Collectors.toMap(k -> k.getKey().toString(), v -> v.getValue().toByteArray()));
    }
}
