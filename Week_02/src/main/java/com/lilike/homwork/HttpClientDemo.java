package com.lilike.homwork;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @Author llk
 * @Date 2020/10/28 21:11
 * @Version 1.0
 */
public class HttpClientDemo {

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8088/api/hello");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            String s = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(s);

        } finally {
            response.close();
        }

    }

}
