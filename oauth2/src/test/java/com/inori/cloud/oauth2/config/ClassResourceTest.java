package com.inori.cloud.oauth2.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.security.KeyPair;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClassResourceTest {

    @Test
    public void testPath() {
        String path = null;
        try {
            path = new ClassPathResource("inori-jwt.jks").getFile().getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(path);
    }

    @Test
    public void testKeyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("inori-jwt.jks"),
                "inori123".toCharArray());

        KeyPair pair = keyStoreKeyFactory.getKeyPair("inori-jwt");

        System.out.println(pair.getPrivate());
    }
}
