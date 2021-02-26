package irita.sdk.util.http;


import irita.sdk.module.base.WrappedRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

// this httpUtils just for connect with block chain
public interface BlockChainHttp {
    String get(String uri);


    /**
     * common post, content-type: application/json
     *
     * @param uri
     * @param body json
     * @return res
     */
    String post(String uri, String body) throws IOException;

    <S extends com.google.protobuf.GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException;

    default String getResponse(InputStream input) throws IOException {
        // return charset and save data
        BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String temp = null;
        while ((temp = br.readLine()) != null) {
            builder.append(temp);
        }
        br.close();
        input.close();
        return builder.toString();
    }

    default boolean http400or500(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST || connection.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR;
    }
}
