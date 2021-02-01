package irita.sdk.module.bank;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.Any;
import cosmos.bank.v1beta1.Tx;
import cosmos.base.v1beta1.CoinOuterClass;
import cosmos.tx.v1beta1.TxOuterClass;
import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;
import irita.sdk.exception.IritaSDKException;
import irita.sdk.util.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.CryptoException;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class BankClient extends Client {
    public BankClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    public void send(String any) throws CryptoException, IOException {
        Tx.MsgSend msg = Tx.MsgSend.newBuilder()
                .addAmount(CoinOuterClass.Coin.newBuilder().setAmount("1").setDenom("stake").build())
                .setFromAddress(this.option.getKeyManager().getAddr())
                .setToAddress("iaa18xcshrf7qwjmmurxxxe6tezw7qeqzjaz2z5326")
                .build();

        TxOuterClass.TxBody body = TxOuterClass.TxBody.newBuilder()
                .addMessages(Any.pack(msg, "/"))
                .setMemo("")
                .setTimeoutHeight(0)
                .build();

        TxOuterClass.Tx tx = super.signTx(body, false);

        Map<String, String> params = new HashMap<>();
        params.put("tx", Base64.getEncoder().encodeToString(tx.toByteArray()));
        String res = postTx(params);

        System.out.println("交易响应: " + res);
    }

    // TODO exception
    private String postTx(Map<String, String> params) {
        Map<String, Object> newParams = new HashMap<>();
        newParams.put("jsonrpc", "2.0");
        newParams.put("id", 1);
        newParams.put("method", "broadcast_tx_commit");
        newParams.put("params", params);

        String s = JSON.toJSONString(newParams);
        String res = HttpUtils.post(this.nodeUri, s);

        if (StringUtils.isEmpty(res)) {
            throw new IritaSDKException("post tx Failed");
        }
        return res;
    }
}
