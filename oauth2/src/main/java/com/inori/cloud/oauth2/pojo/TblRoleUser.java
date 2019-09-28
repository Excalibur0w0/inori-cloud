package com.inori.cloud.oauth2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblRoleUser {
    @Column
    private String roleId;
    @Column
    private String userId;
    @Id
    private String uuid;
}

