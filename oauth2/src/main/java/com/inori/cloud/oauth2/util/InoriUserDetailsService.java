package com.inori.cloud.oauth2.util;

import com.inori.cloud.oauth2.dao.RoleDao;
import com.inori.cloud.oauth2.dao.RoleUserDao;
import com.inori.cloud.oauth2.dao.UserDao;
import com.inori.cloud.oauth2.pojo.TblRole;
import com.inori.cloud.oauth2.pojo.TblRoleUser;
import com.inori.cloud.oauth2.pojo.TblUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Log4j2
@Service
public class InoriUserDetailsService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("running: " + username);
        TblUser user = userDao.findByUname(username);
        TblRoleUser crit = new TblRoleUser();
        crit.setUserId(user.getUuid());
        Example<TblRoleUser> example = Example.of(crit);
        List<InoriGrantedAuthority> authorities = new LinkedList<>();

        roleUserDao.findAll(example).forEach(ru -> {
            roleDao.findById(ru.getRoleId()).ifPresent(role -> authorities.add(new InoriGrantedAuthority(role)));
        });

        return new InroiUserDetails(authorities, user);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class InoriGrantedAuthority implements GrantedAuthority {
        private TblRole role;

        @Override
        public String getAuthority() {
            System.out.println(role.getRoleCode());
            return role.getRoleCode();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class InroiUserDetails implements UserDetails {
        private List<InoriGrantedAuthority> authorities;
        private TblUser curUser;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return curUser.getUpass();
        }

        @Override
        public String getUsername() {
            return curUser.getUname();
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
