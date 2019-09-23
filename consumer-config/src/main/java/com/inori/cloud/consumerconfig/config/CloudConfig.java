package com.inori.cloud.consumerconfig.config;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@ToString
@Component
public class ClientConfig {
    @Value("${info.name}")
    String name;
}
