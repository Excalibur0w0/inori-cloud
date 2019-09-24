package com.inori.cloud.providerauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthConfig {
    @Value("info.db.connectStr")
    private String connectStr;
}
