package irita.sdk.util.http;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.GeneratedMessageV3;
import irita.sdk.module.base.WrappedRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpbProjectKeyHttpUtils implements BlockChainHttp {
    private final String projectKey;
    private static final String OPB_PROJECT_KEY_HEADER = "x-api-key";

    public OpbProjectKeyHttpUtils(String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public String get(String uri) {
        return new CommonHttpUtils().get(uri);
    }

    @Override
    public String post(String uri, String body) throws IOException {
        HttpURLConnection connection;
        InputStream is;
        OutputStream os;
        String result;
        URL url;

        url = new URL(uri);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(60000);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty(OPB_PROJECT_KEY_HEADER, projectKey); // different between commonHttpUtils
        connection.connect(); // require a connect

        os = connection.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        osw.write(body);
        osw.flush();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            is = connection.getInputStream();
            result = getResponse(is);
        } else {
            throw new IOException("connect error, httpCode:" + connection.getResponseCode());
        }
        return result;
    }

    @Override
    public <S extends GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException {
        return post(uri, JSON.toJSONString(object));
    }
}
