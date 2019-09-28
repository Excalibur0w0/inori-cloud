package com.inori.cloud.providerauth.rsa;

import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

public class RsaDecodeTest {
    @Test
    public void test() {
        Resource resource = new ClassPathResource("public.cer");
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            System.out.println(publicKey);
            RsaVerifier verifier = new RsaVerifier(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        String args = "$2a$10$h55prsDH5Nb3dvBGjy.JF.olZBYMuhXnOien/DdriP2bFt36K.1jS";
        String pwd = "exc";
        String args2 = BPwdEncoderUtil.BCryptPassword(pwd);

        System.out.println(BPwdEncoderUtil.matches(pwd, args));
        System.out.println(BPwdEncoderUtil.matches(pwd, args2));
    }

    @Test
    public void encodeBase64() {
        String ori = "provider-auth:123456";

        byte[] base = Base64Utils.encode(ori.getBytes());
        System.out.println(new String(base));
    }
}