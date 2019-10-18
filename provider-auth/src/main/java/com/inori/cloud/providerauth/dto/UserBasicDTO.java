package com.inori.cloud.providerauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserBasicDTO {
    private String uuid;
    private String uname;
    private Integer age;
    private String city;
}
