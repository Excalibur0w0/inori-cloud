package com.inori.cloud.oauth2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblRole implements Serializable {
    @Id
    private String uuid;
    @Column
    private String roleName;
    @Column
    private String roleCode;
}
