package com.inori.cloud.providerauth.init;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.pojo.JWT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGetToken {
    @Autowired
    private AuthServiceClient client;

    @Test public void test() {
        JWT jwt = client.getToken("Basic cHJvdmlkZXItYXV0aDoxMjM0NTY=", "password", "test", "test");

        System.out.println(jwt.getAccess_token());
    }

}
