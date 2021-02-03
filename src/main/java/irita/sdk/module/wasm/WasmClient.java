package irita.sdk.module.wasm;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.ByteString;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import cosmwasm.wasm.v1beta1.QueryGrpc;
import cosmwasm.wasm.v1beta1.QueryOuterClass;
import cosmwasm.wasm.v1beta1.Tx;
import cosmwasm.wasm.v1beta1.Types;
import io.grpc.ManagedChannel;
import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;
import irita.sdk.constant.TxStatus;
import irita.sdk.constant.enums.EventEnum;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.module.base.*;
import irita.sdk.util.HttpUtils;
import irita.sdk.util.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WasmClient extends Client {
    public WasmClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    // upload the contract to block-chain and return the codeId for user
    public String store(StoreRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());

        if (req.getWasmByteCode() != null) {
            req.setWasmByteCode(req.getWasmByteCode());
        } else {
            byte[] bytes = IOUtils.readAll(req.getWasmFile());
            if (bytes == null) {
                throw new IritaSDKException("file not read");
            }
            req.setWasmByteCode(bytes);
        }

        Tx.MsgStoreCode msg = Tx.MsgStoreCode.newBuilder()
                .setSender(account.getAddress())
                .setWasmByteCode(ByteString.copyFrom(req.getWasmByteCode()))
                .setSource("")
                .setBuilder("")
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        ResultTx resultTx = checkResTxAndConvert(res);

        return resultTx.getEventValue(EventEnum.MESSAGE_CODE_ID);
    }

    // instantiate the contract state
    public String instantiate(InstantiateRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgInstantiateContract.Builder builder = Tx.MsgInstantiateContract.newBuilder()
                .setSender(account.getAddress())
                .setAdmin(Optional.of(req).map(InstantiateRequest::getAdmin).orElse(""))
                .setCodeId(req.getCodeId())
                .setInitMsg(ByteString.copyFrom(JSON.toJSONString(req.getInitMsg()).getBytes(StandardCharsets.UTF_8)))
                .setLabel(req.getLabel());

        if (req.getInitFunds() != null) {
            builder.addInitFunds(
                    CoinOuterClass.Coin.newBuilder()
                            .setDenom(req.getInitFunds().getDenom())
                            .setAmount(req.getInitFunds().getAmount()));
        }

        Tx.MsgInstantiateContract msg = builder.build();
        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        ResultTx resultTx = checkResTxAndConvert(res);

        return resultTx.getEventValue(EventEnum.MESSAGE_CONTRACT_ADDRESS);
    }

    // execute the contract method
    public ResultTx execute(String contractAddress, ContractABI abi, Coin funds, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        byte[] msgBytes = abi.build();

        Tx.MsgExecuteContract.Builder builder = Tx.MsgExecuteContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .setMsg(ByteString.copyFrom(msgBytes));

        if (funds != null) {
            builder.addSentFunds(
                    CoinOuterClass.Coin.newBuilder()
                            .setAmount(funds.getAmount())
                            .setDenom(funds.getDenom()));
        }

        Tx.MsgExecuteContract msg = builder.build();
        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(baseTx, body, false);

        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        return checkResTxAndConvert(res);
    }

    public ResultTx migrate(String contractAddress, long newCodeID, byte[] msgByte) throws IOException {
        Account account = super.queryAccount(option.getKeyManager().getAddr());
        Tx.MsgMigrateContract msg = Tx.MsgMigrateContract.newBuilder()
                .setSender(account.getAddress())
                .setContract(contractAddress)
                .setCodeId(newCodeID)
                .setMigrateMsg(ByteString.copyFrom(msgByte))
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(null, body, false);
        String res = HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
        return checkResTxAndConvert(res);
    }

    // return the contract information
    public ContractInfo queryContractInfo(String contractAddress) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryContractInfoRequest req = QueryOuterClass.QueryContractInfoRequest
                .newBuilder()
                .setAddress(contractAddress)
                .build();

        QueryOuterClass.QueryContractInfoResponse resp = QueryGrpc.newBlockingStub(channel).contractInfo(req);
        channel.shutdown();
        return Convert.toContractInfo(resp);
    }

    // execute contract's query method and return the result
    public byte[] queryContract(String address, ContractABI abi) {
        ManagedChannel channel = super.getGrpcClient();

        byte[] msgBytes = abi.build();
        QueryOuterClass.QuerySmartContractStateRequest req = QueryOuterClass.QuerySmartContractStateRequest
                .newBuilder()
                .setAddress(address)
                .setQueryData(ByteString.copyFrom(msgBytes))
                .build();

        QueryOuterClass.QuerySmartContractStateResponse resp = QueryGrpc.newBlockingStub(channel).smartContractState(req);
        channel.shutdown();
        return resp.toByteArray();
    }

    // export all state data of the contract
    public Map<String, String> exportContractState(String address) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryAllContractStateRequest req = QueryOuterClass.QueryAllContractStateRequest
                .newBuilder()
                .setAddress(address)
                .build();

        QueryOuterClass.QueryAllContractStateResponse resp = QueryGrpc.newBlockingStub(channel).allContractState(req);
        channel.shutdown();

        Map<String, String> map = new HashMap<>();
        List<Types.Model> models = resp.getModelsList();
        for (Types.Model model : models) {
            byte[] bytes = model.getKey().toByteArray();
            final int PREFIX = 2;
            byte[] dest = new byte[bytes.length - PREFIX];
            System.arraycopy(bytes, PREFIX, dest, 0, dest.length);

            String key = new String(dest);
            String value = new String(model.getValue().toByteArray());
            map.put(key, value);
        }
        return map;
    }

    private ResultTx checkResTxAndConvert(String res) {
        ResultTx resultTx = JSON.parseObject(res, ResultTx.class);

        if (resultTx.getCode() != TxStatus.SUCCESS) {
            throw new IritaSDKException(resultTx.getLog());
        }
        return resultTx;
    }
}
