package irita.sdk.module.bank;

import irita.sdk.client.Client;
import irita.sdk.client.IritaClientOption;
import irita.sdk.model.WrappedRequest;
import irita.sdk.util.HttpUtils;
import proto.cosmos.bank.v1beta1.Tx;
import proto.cosmos.base.v1beta1.CoinOuterClass;
import proto.cosmos.tx.v1beta1.TxOuterClass;

import java.io.IOException;

public class BankClient extends Client {
    public BankClient(String nodeUri, String grpcAddr, String chainId, IritaClientOption option) {
        this.nodeUri = nodeUri;
        this.grpcAddr = grpcAddr;
        this.chainId = chainId;
        this.option = option;
    }

    public String send(String amount, String toAddress) throws IOException {
        Tx.MsgSend msg = Tx.MsgSend.newBuilder()
                .addAmount(CoinOuterClass.Coin.newBuilder()
                        .setAmount(amount)
                        .setDenom(this.option.getFee().denom)
                        .build())
                .setFromAddress(option.getKeyManager().getAddr())
                .setToAddress(toAddress)
                .build();

        TxOuterClass.TxBody body = super.buildTxBody(msg);
        TxOuterClass.Tx tx = super.signTx(null, body, false);
        return HttpUtils.post(nodeUri, new WrappedRequest<>(tx));
    }
}
