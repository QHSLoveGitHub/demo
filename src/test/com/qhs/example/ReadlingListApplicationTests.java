package com.qhs.example;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.qhs.example.utils.FilterUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.util.StringUtils;

import java.util.Date;

@SpringBootTest
public class ReadlingListApplicationTests {

    @Test
    public void contextLoads(){

    }

    @Test
    public void testMain(){
       byte[] bs = new byte[]{1,-1};
       String by2Hex = FilterUtil.byte2Hex(bs);
       System.out.println(by2Hex);
    }

    @Test
    public void testHttpClient() throws  Exception{
        //参数
        String paasId = "业务系统分配的paasid";
        String paasToken = "业务系统分配的paastoken";
        String apiPath = "/ebus/org/getuserbyuserid(网关接口地址)";
        String serverUrl="http://111.230.222.11:8080(api网关地址)";
        String params = "{\"uid\":\"5b07b3de672c4e999c4b04f4\"}"; //请求接口的参数

        long now = new Date().getTime();
        String timestamp = Long.toString((long) Math.floor(now / 1000));
        String nonce = Long.toHexString(now) + "-" + Long.toHexString((long) Math.floor(Math.random() * 0xFFFFFF));
        String signature=FilterUtil.toSHA256(timestamp + paasToken + nonce + timestamp);

//        System.out.println(0xFFFFFF);
//        System.out.println(Math.floor(0xFFFFFF));
//        System.out.println(Math.floor(Math.random() * 0xFFFFFF));

        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(serverUrl+apiPath);
        httpPost.setHeader("x-tif-paasid", paasId);
        httpPost.setHeader("x-tif-timestamp", timestamp);
        httpPost.setHeader("x-tif-signature", signature);
        httpPost.setHeader("x-tif-nonce", nonce);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Cache-Control", "no-cache");
        HttpEntity entity=new StringEntity(params);

        httpPost.setEntity(entity);

        CloseableHttpResponse response=client.execute(httpPost);
        System.out.println(EntityUtils.toString(response.getEntity()));

    }
}
