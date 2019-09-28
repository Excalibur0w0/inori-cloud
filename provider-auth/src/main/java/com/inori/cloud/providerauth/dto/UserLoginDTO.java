package com.inori.cloud.providerauth.dto;

import com.inori.cloud.providerauth.pojo.JWT;
import com.inori.cloud.providerauth.pojo.TblUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    private TblUser user;
    private JWT jwt;
}
