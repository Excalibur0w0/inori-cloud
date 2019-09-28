package com.inori.cloud.oauth2.dao;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class PasswordTest {
    @Test
    public void test() {
//        String pass = new BCryptPasswordEncoder().encode("123456");
//        $2a$10$eZkjv/YIv6w1dvxj5XaYwuq4TN.Uxh4Mhrxg/98dv68zID/Hq/dPq
//        $2a$10$U90pqD7crn190Te.fhmPgeEhLU4.fxZ67lnAIPLtLR.5skN4mlbDS
//        System.out.println(pass);
//        System.out.println(pass.length());

        boolean a = new BCryptPasswordEncoder().matches("123456", "$2a$10$U90pqD7crn190Te.fhmPgeEhLU4.fxZ67lnAIPLtLR.5skN4mlbDS");
        System.out.println(a);
    }

    @Test
    public void testMatcher() {
        Pattern BCRYPT_PATTERN = Pattern
                .compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
        boolean res = BCRYPT_PATTERN.matcher("$2a$10$h55prsDH5Nb3dvBGjy.JF.olZBYMuhXnOien/DdriP2bFt36K.1jS").matches();

        System.out.println(res);
    }
}
