package com.inori.cloud.providerauth.pojo;

import lombok.Data;

@Data
public class JWT {
    private String access_token, token_type, refresh_token, scope, jti;
    private int expires_in;
}
