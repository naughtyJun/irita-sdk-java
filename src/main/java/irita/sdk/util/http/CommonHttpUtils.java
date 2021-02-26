package irita.sdk.util.http;


import com.alibaba.fastjson.JSON;
import irita.sdk.module.base.WrappedRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CommonHttpUtils implements BlockChainHttp {

    // this get just for call lcd, if you want use a common get, you will need refactor
    @Override
    public String get(String uri) {
        HttpURLConnection connection;
        InputStream is;
        String result = null;
        URL url;

        try {
            url = new URL(uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            // send req to server
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                result = getResponse(is);
            } else if (http400or500(connection)) {
                is = connection.getErrorStream();
                result = getResponse(is);
            } else {
                throw new RuntimeException("connect error:" + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * common post, content-type: application/json
     *
     * @param uri
     * @param body json
     * @return res
     */
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

    // use this to send tx
    @Override
    public <S extends com.google.protobuf.GeneratedMessageV3> String post(String uri, WrappedRequest<S> object) throws IOException {
        return post(uri, JSON.toJSONString(object));
    }
}
