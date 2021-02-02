package sdk;

import irita.sdk.util.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IOUtilTest {
    @Test
    public void readAll() {
        byte[] bytes = IOUtils.readAll("src/main/resources/test.wasm");
        assertNotNull(bytes);
        assertEquals(146310, bytes.length);
    }
}
