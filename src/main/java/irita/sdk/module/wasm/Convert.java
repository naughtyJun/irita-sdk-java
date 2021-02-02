package irita.sdk.module.wasm;

import cosmos.wasm.QueryOuterClass;
import cosmos.wasm.Wasm;

public class Convert {
    public static ContractInfo toContractInfo(QueryOuterClass.QueryContractInfoResponse resp) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setCodeId(resp.getContractInfo().getCodeId());
        contractInfo.setCreator(resp.getContractInfo().getCreator());
        contractInfo.setAdmin(resp.getContractInfo().getAdmin());
        contractInfo.setLabel(resp.getContractInfo().getLabel());

        Wasm.AbsoluteTxPosition absoluteTxPosition = resp.getContractInfo().getCreated();
        ContractInfo.AbsoluteTxPosition created = new ContractInfo.AbsoluteTxPosition();

        created.BlockHeight = absoluteTxPosition.getBlockHeight();
        created.TxIndex = absoluteTxPosition.getTxIndex();
        contractInfo.setCreated(created);
        return contractInfo;
    }
}
