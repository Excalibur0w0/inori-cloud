package com.inori.cloud.oauth2.util;

import com.inori.cloud.oauth2.dao.*;
import com.inori.cloud.oauth2.pojo.*;
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
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionRoleDao permissionRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("running: " + username);
        TblUser user = userDao.findByUname(username);
        TblRoleUser crit = new TblRoleUser();
        crit.setUserId(user.getUuid());
        Example<TblRoleUser> example = Example.of(crit);
        List<GrantedAuthority> authorities = new LinkedList<>();

        roleUserDao.findAll(example).forEach(ru -> {
            roleDao.findById(ru.getRoleId()).ifPresent(role -> {
                authorities.add(new RoleGrantedAuthority(role));
                // 根据roleId查找permisison
                TblPermissionRole crit2 = new TblPermissionRole();
                crit2.setRoleId(role.getUuid());

                permissionRoleDao.findAll(Example.of(crit2)).forEach(pr -> {
                    permissionDao.findById(pr.getPermissionId()).ifPresent(p ->
                            authorities.add(new PermissionGrantedAuthority(p)));
                });
            });
        });

        return new InroiUserDetails(authorities, user);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class RoleGrantedAuthority implements GrantedAuthority {
        private TblRole role;

        @Override
        public String getAuthority() {
//            log.info("调用了getAuthority: " + role.getRoleCode());
            return "ROLE_" + role.getRoleCode();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class PermissionGrantedAuthority implements GrantedAuthority {
        private TblPermission permission;

        @Override
        public String getAuthority() {
            return permission.getPermissionCode();
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class InroiUserDetails implements UserDetails {
        private List<GrantedAuthority> authorities;
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
