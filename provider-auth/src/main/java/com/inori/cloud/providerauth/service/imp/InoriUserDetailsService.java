package com.inori.cloud.providerauth.service.imp;

import com.inori.cloud.providerauth.client.AuthServiceClient;
import com.inori.cloud.providerauth.dto.UserLoginDTO;
import com.inori.cloud.providerauth.pojo.JWT;
import com.inori.cloud.providerauth.pojo.TblPermission;
import com.inori.cloud.providerauth.pojo.TblRole;
import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.AuthService;
import com.inori.cloud.providerauth.util.BPwdEncoderUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
public class InoriUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthServiceClient client;

    public boolean register(TblUser user) {
        return authService.addUser(user);
    }

    public List<TblRole> getRolesByUsername(String username) {
        TblUser user = authService.getUserByUsername(username);

        return authService.getRolesByUser(user);
    }

    public UserLoginDTO login(String username, String pwd) {
        TblUser user = authService.getUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!BPwdEncoderUtil.matches(pwd, user.getUpass())) {
            throw new RuntimeException("debug: 用户密码不正确! 密码: " + pwd +" 密码: " + user.getUpass());
        }
        // 客户端 provider-auth:123456 的base64缩写
        JWT jwt = client.getToken("Basic cHJvdmlkZXItYXV0aDoxMjM0NTY=", "password", username, pwd);
        if (jwt == null) {
            throw new RuntimeException("用户token有问题");
        }
        UserLoginDTO dto = new UserLoginDTO();
        dto.setJwt(jwt);
        dto.setUser(user);

        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername被调用");
        TblUser user = authService.getUserByUsername(username);
        InoriUserDetails details = new InoriUserDetails()
                .setUser(user).setRoles(authService.getRolesByUser(user));

        return details;
    }

    public class InoriUserDetails implements  UserDetails {
        private TblUser user;
        private List<RoleAuthority> roles;

        public InoriUserDetails setUser(TblUser user) {
            this.user = user;
            return this;
        }

        public InoriUserDetails setRoles(List<TblRole> roles) {
            if (roles == null) {
                roles = new LinkedList<>();
            }
            for (TblRole role :
                    roles) {
                this.roles.add(new RoleAuthority(role));
            }
            return this;
        }

        private class RoleAuthority implements GrantedAuthority {
            private TblRole role;

            public RoleAuthority(TblRole role) {
                this.role = role;
            }

            @Override
            public String getAuthority() {
                System.out.println("ROLE_" + role.getRoleCode());
                return "ROLE_" + role.getRoleCode();
            }
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.roles;
        }

        @Override
        public String getPassword() {
            return user.getUpass();
        }

        @Override
        public String getUsername() {
            return user.getUname();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
