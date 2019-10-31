package com.inori.cloud.oauth2.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TblUser implements Serializable {
    @Id
    private String uuid;
    @Column(nullable = false, unique = true)
    private String uname;
    @Column
    private String upass;
    @Column
    private Date birthday;
    @Column
    private String email;
    @Column
    private String gender;
    @Column
    private String description;
    @Column
    private String city;
}

