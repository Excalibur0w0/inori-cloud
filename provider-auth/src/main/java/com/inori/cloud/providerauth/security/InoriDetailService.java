package com.inori.cloud.providerauth.security;

import com.inori.cloud.providerauth.pojo.TblUser;
import com.inori.cloud.providerauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class InoriDetailService implements UserDetailsService {
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TblUser user = authService.getUserById(username);
        UserDetails details = new
    }

    public class InoriUserDetails implements  UserDetails {
        private TblUser user;
        // need to create
        private TblRole role;
        private TblPermission permission;

        public InoriUserDetails() {

        }

        public InoriUserDetails(TblUser tblUser) {
            this.user = tblUser;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    }
}
