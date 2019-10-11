package com.inori.cloud.providerauth.client;

import com.inori.cloud.providerauth.pojo.JWT;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        log.info("getToken融断!");
        return null;
    }
}
