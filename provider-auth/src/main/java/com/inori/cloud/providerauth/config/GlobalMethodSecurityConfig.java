package com.inori.cloud.providerauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级别验证
public class GlobalMethodSecurityConfig {
}
