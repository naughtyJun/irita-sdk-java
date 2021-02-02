package irita.sdk.module.community_gov;

import com.alibaba.fastjson.JSON;
import irita.sdk.constant.ContractArg;
import irita.sdk.constant.ContractMethod;
import irita.sdk.module.base.Coin;
import irita.sdk.module.base.Result;
import irita.sdk.module.wasm.ContractABI;
import irita.sdk.module.wasm.WasmClient;
import org.bouncycastle.crypto.CryptoException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CommunityGovClient {
    private final WasmClient wasmClient;

    public CommunityGovClient(WasmClient wasmClient) {
        this.wasmClient = wasmClient;
    }

    // TODO addDepartment, updateDepartment, addMember, removeMember, removeHash ...


    /**
     * Hash创建（根据证照类型及唯一性ID, 判断本地是否已存在相应的票面信息Hash及证照文件Hash, 如存在，则将链上所有此证照的历史Hash变为删除态）
     *
     * @param docType  证照类型, 必填
     * @param docId    证照唯一性Id, 必填
     * @param strHash  文件hash, 当file_hash为空时必填
     * @param fileHash 字符hash, 当str_hash为空时必填
     */
    public void addHash(String docType, String docId, String strHash, String fileHash) throws CryptoException, IOException {
        ContractABI abi = new ContractABI();
        abi.setMethod(ContractMethod.ADD_HASH);
        Map<String, Object> args = new HashMap<>();
        args.put(ContractArg.DOC_TYPE, docType);
        args.put(ContractArg.DOC_ID, docId);
        args.put(ContractArg.STR_HASH, strHash);
        args.put(ContractArg.FILE_HASH, fileHash);
        abi.setArgs(args);

        // TODO 把contractAddress 合约地址配进去
        Result execute = wasmClient.execute("", abi, new Coin());
        System.out.println(JSON.toJSONString(execute));
    }

    /**
     * 根据strHash或者fileHash查找 hash是否存在
     *
     * @param strHash  文件hash, 当file_hash为空时必填
     * @param fileHash 字符hash, 当str_hash为空时必填
     */
    public Boolean getHash(String strHash, String fileHash) throws CryptoException, IOException {
        ContractABI abi = new ContractABI();
        abi.setMethod(ContractMethod.ADD_HASH);
        Map<String, Object> args = new HashMap<>();
        args.put(ContractArg.STR_HASH, strHash);
        args.put(ContractArg.FILE_HASH, fileHash);
        abi.setArgs(args);

        // TODO 把contractAddress 合约地址配进去
        Result execute = wasmClient.execute("", abi, new Coin());
        System.out.println(JSON.toJSONString(execute));

        // TODO when send failed
        return false;
    }
}
