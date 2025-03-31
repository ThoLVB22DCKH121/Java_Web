package com.example.springboot.service.Impl;


import com.example.springboot.repository.entity.AppUser;
import com.example.springboot.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        String fullName = appUser.getLastname() + " " + appUser.getFirstname();
        if (appUser != null) {
            return new UserDetailsImpl(
                    appUser.getId(),
                    appUser.getUsername(),
                    appUser.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+appUser.getRole().name())),
                    fullName
            );
        }
        return null;
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public void updateUser(Integer idUser,AppUser updateAppUser) {
        Optional<AppUser> appUser = appUserRepository.findById(idUser);
        if (appUser.isPresent()) {
            AppUser user = appUser.get();
            user.setRole(updateAppUser.getRole());
            appUserRepository.save(user);
        }
    }

    public AppUser getUserById(Integer idUser) {
        Optional<AppUser> appUser = appUserRepository.findById(idUser);
        return appUser.get();
    }
}
