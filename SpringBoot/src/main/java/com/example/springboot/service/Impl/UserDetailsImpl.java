package com.example.springboot.service.Impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
        private Integer id;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private String fullName;

    public UserDetailsImpl(Integer id, String username, String password, Collection<? extends GrantedAuthority> authorities, String fullName) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.authorities = authorities;
            this.fullName = fullName;
        }

        public Integer getId() {
            return id;
        }
        public String getFullName() {
            return fullName;
        }
        @Override
        public String getUsername() { return username; }

        @Override
        public String getPassword() { return password; }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

        @Override
        public boolean isAccountNonExpired() { return true; }

        @Override
        public boolean isAccountNonLocked() { return true; }

        @Override
        public boolean isCredentialsNonExpired() { return true; }

        @Override
        public boolean isEnabled() { return true; }

}
