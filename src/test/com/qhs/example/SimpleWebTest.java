package com.qhs.example;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimpleWebTest {

    @Value("${local.server.port}")
    private int port;

    //
    @Test
    public void pageNotFound() {
        try{
            //
            RestTemplate rest = new RestTemplate();
            rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
        }catch (Exception e){
            throw e;
        }
    }
}
