package irita.sdk.module.nft;


import io.grpc.ManagedChannel;
import irita.sdk.model.Account;
import irita.sdk.model.BaseTx;
import irita.sdk.model.ResultTx;
import irita.sdk.new_client.BaseClient;
import irita.sdk.util.AddressUtils;
import org.apache.commons.lang3.StringUtils;
import proto.cosmos.base.query.v1beta1.Pagination;
import proto.nft.Nft;
import proto.nft.QueryGrpc;
import proto.nft.QueryOuterClass;
import proto.nft.Tx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NftClient extends BaseClient {
    public ResultTx issueDenom(IssueDenomRequest req, BaseTx baseTx) throws IOException {
        Account account = super.queryAccount();
        Tx.MsgIssueDenom msg = Tx.MsgIssueDenom
                .newBuilder()
                .setId(req.getId())
                .setName(req.getName())
                .setSchema(req.getSchema())
                .setSender(account.getAddress())
                .build();

        return super.buildAndSend(msg, baseTx, account);
    }

    public ResultTx mintNft(MintNFTRequest req, BaseTx baseTx) throws IOException {
        Tx.MsgMintNFT.Builder builder = Tx.MsgMintNFT
                .newBuilder()
                .setDenomId(req.getDenom())
                .setId(req.getId())
                .setName(req.getName())
                .setUri(req.getUri())
                .setData(req.getData());

        if (StringUtils.isNotEmpty(req.getRecipient())) {
            String recipient = req.getRecipient();
            AddressUtils.validAddress(recipient);
            builder.setRecipient(recipient);
        }
        Tx.MsgMintNFT msg = builder.build();
        return super.buildAndSend(msg, baseTx);
    }

    public ResultTx editNft(EditNFTRequest req, BaseTx baseTx) throws IOException {
        Tx.MsgEditNFT msg = Tx.MsgEditNFT
                .newBuilder()
                .setDenomId(req.getDenom())
                .setId(req.getId())
                .setName(req.getName())
                .setUri(req.getUri())
                .setData(req.getData()).build();
        return super.buildAndSend(msg, baseTx);
    }

    public ResultTx transferNFt(TransferNFTRequest req, BaseTx baseTx) throws IOException {
        Tx.MsgTransferNFT.Builder builder = Tx.MsgTransferNFT
                .newBuilder()
                .setDenomId(req.getDenom())
                .setId(req.getId())
                .setUri(req.getUri())
                .setData(req.getData())
                .setName(req.getName());

        if (StringUtils.isNotEmpty(req.getRecipient())) {
            String recipient = req.getRecipient();
            AddressUtils.validAddress(recipient);
            builder.setRecipient(recipient);
        }
        Tx.MsgTransferNFT msg = builder.build();
        return super.buildAndSend(msg, baseTx);
    }

    public ResultTx burnNft(BurnNFTRequest req, BaseTx baseTx) throws IOException {
        Tx.MsgBurnNFT msg = Tx.MsgBurnNFT
                .newBuilder()
                .setDenomId(req.getDenom())
                .setId(req.getId()).build();
        return super.buildAndSend(msg, baseTx);
    }


    public long querySupply(String denomID, String owner) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QuerySupplyRequest req = QueryOuterClass.QuerySupplyRequest
                .newBuilder()
                .setDenomId(denomID)
                .setOwner(owner)
                .build();

        QueryOuterClass.QuerySupplyResponse resp = QueryGrpc.newBlockingStub(channel).supply(req);
        return resp.getAmount();
    }

    public QueryOwnerResp queryOwner(String denomID, String ownerD) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryOwnerRequest req = QueryOuterClass.QueryOwnerRequest
                .newBuilder()
                .setDenomId(denomID)
                .setOwner(ownerD)
                .build();

        QueryOuterClass.QueryOwnerResponse resp = QueryGrpc.newBlockingStub(channel).owner(req);
        return Convert.toQueryOwnerResp(resp.getOwner());
    }

    public QueryCollectionResp queryCollection(String denomID, Pagination.PageRequest page) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryCollectionRequest.Builder builder = QueryOuterClass.QueryCollectionRequest
                .newBuilder()
                .setDenomId(denomID);
        if (page != null) {
            builder.setPagination(page);
        }
        QueryOuterClass.QueryCollectionRequest req = builder.build();

        QueryOuterClass.QueryCollectionResponse resp = QueryGrpc.newBlockingStub(channel).collection(req);
        return Convert.toQueryCollectionResp(resp.getCollection());
    }

    public QueryDenomResp queryDenom(String denomID) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryDenomRequest req = QueryOuterClass.QueryDenomRequest
                .newBuilder()
                .setDenomId(denomID)
                .build();
        QueryOuterClass.QueryDenomResponse resp = QueryGrpc.newBlockingStub(channel).denom(req);
        return Convert.toQueryDenomResp(resp.getDenom());
    }

    public List<QueryDenomResp> queryDenoms(Pagination.PageRequest page) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryDenomsRequest.Builder builder = QueryOuterClass.QueryDenomsRequest.newBuilder();
        if (page != null) {
            builder.setPagination(page);
        }
        QueryOuterClass.QueryDenomsRequest req = builder.build();

        QueryOuterClass.QueryDenomsResponse resp = QueryGrpc.newBlockingStub(channel).denoms(req);
        return Convert.toListQueryDenomResp(resp.getDenomsList());
    }

    public QueryNFTResp queryNFT(String denomID, String nftID) {
        ManagedChannel channel = super.getGrpcClient();
        QueryOuterClass.QueryNFTRequest req = QueryOuterClass.QueryNFTRequest
                .newBuilder()
                .setDenomId(denomID)
                .setTokenId(nftID)
                .build();

        QueryOuterClass.QueryNFTResponse resp = QueryGrpc.newBlockingStub(channel).nFT(req);
        return Convert.toQueryNFTResp(resp.getNft());
    }

    private static class Convert {
        public static QueryOwnerResp toQueryOwnerResp(Nft.Owner owner) {
            String address = owner.getAddress();
            List<Nft.IDCollection> idCollectionsList = owner.getIdCollectionsList();

            List<IDC> idcs = new ArrayList<>();
            for (Nft.IDCollection idc : idCollectionsList) {
                String denomId = idc.getDenomId();
                List<String> tokenIDs = new ArrayList<>(idc.getTokenIdsList());
                idcs.add(new IDC(denomId, tokenIDs));
            }

            QueryOwnerResp res = new QueryOwnerResp();
            res.setAddress(address);
            res.setIdcs(idcs);
            return res;
        }

        public static QueryCollectionResp toQueryCollectionResp(Nft.Collection collection) {
            Nft.Denom denom = collection.getDenom();
            List<Nft.BaseNFT> nftList = collection.getNftsList();

            List<QueryNFTResp> nfts = new ArrayList<>();
            for (Nft.BaseNFT baseNFT : nftList) {
                QueryNFTResp nftResp = toQueryNFTResp(baseNFT);
                nfts.add(nftResp);
            }

            QueryCollectionResp res = new QueryCollectionResp();
            res.setDenom(new QueryDenomResp(denom.getId(), denom.getName(), denom.getSchema(), denom.getCreator()));
            res.setNfts(nfts);
            return res;
        }

        public static QueryDenomResp toQueryDenomResp(Nft.Denom denom) {
            QueryDenomResp res = new QueryDenomResp();
            res.setId(denom.getId());
            res.setName(denom.getName());
            res.setSchema(denom.getSchema());
            res.setCreator(denom.getCreator());
            return res;
        }


        public static List<QueryDenomResp> toListQueryDenomResp(List<Nft.Denom> denomsList) {
            List<QueryDenomResp> res = new ArrayList<>();
            for (Nft.Denom v : denomsList) {
                res.add(toQueryDenomResp(v));
            }
            return res;
        }

        public static QueryNFTResp toQueryNFTResp(Nft.BaseNFT baseNFT) {
            QueryNFTResp res = new QueryNFTResp();
            res.setId(baseNFT.getId());
            res.setName(baseNFT.getName());
            res.setUri(baseNFT.getUri());
            res.setData(baseNFT.getData());
            res.setCreator(baseNFT.getOwner());
            return res;
        }
    }
}

