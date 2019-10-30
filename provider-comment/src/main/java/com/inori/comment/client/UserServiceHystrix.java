package com.inori.comment.client;

import com.netflix.hystrix.HystrixCommand;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserServiceHystrix implements UserServiceClient {
    @Override
    public String getUserInfoByToken(String authorization) {
        log.info("getUserInfoByToken融断!");
        return null;
    }

    @Override
    public HystrixCommand<String> getUserBasicInfo(String authorization, String userId) {
        log.info("getUserBasicInfo熔断");
        return null;
    }
}