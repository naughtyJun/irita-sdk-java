package irita.sdk;

import irita.sdk.exception.ContractException;
import irita.sdk.util.HttpUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class HttpUtilsTest {
    @Test
    public void get() throws Exception {
        String res = HttpUtils.get("http://localhost:26657");
        System.out.println(res);
    }

    @Test
    public void getWithParam() {
        String res = null;
        try {
            res = HttpUtils.get("http://106.14.211.224:30152/wasm/v1beta1/contract/iaa18vd8fpwxzck93qlwghaj6arh4p7c5n89fqcgm9/smart/eyJnZXRfaGFzaCI6eyJoYXNoIjoiOGU3Yzg0N2YzYjBhN2NlZTI1Njk0OTM1NDBiMDJhNjliMjEzY2VmNWE4ZmNjYjhjMzA2NGMyOWQ3OTExNzgzYiJ9fQ%3D%3D");
        } catch (ContractException e) {
            e.printStackTrace();
        }
        System.out.println(res);
    }

    @Test
    public void post() throws Exception {
        String res = HttpUtils.post("http://localhost:8085/test-post", "");
        System.out.println(res);
    }
}
