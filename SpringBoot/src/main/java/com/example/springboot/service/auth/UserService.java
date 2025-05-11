package com.example.springboot.service.auth;


import com.example.springboot.enums.UserRole;
import com.example.springboot.models.RegisterDTO;
import com.example.springboot.repository.entity.User;
import com.example.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new UserDetailsImpl(
                    userEntity.getId(),
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_"+ userEntity.getRole().name())),
                    userEntity.getFullname()
            );
        }
        return null;
    }

    public Page<User> searchUsers(int page,int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.searchUser(keyword, pageable);
    }

    public  boolean updateUser(Long idUser, User updateUser) {
        Optional<User> User = userRepository.findById(idUser);
        if (User.isPresent()) {
            User user = User.get();
            user.setUsername(updateUser.getUsername());
            user.setFullname(updateUser.getFullname());
            user.setAddress(updateUser.getAddress());
            user.setEmail(updateUser.getEmail());
            user.setPhone(updateUser.getPhone());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User getUserById(Long idUser) {
        Optional<User> User = userRepository.findById(idUser);
        return User.get();
    }

    public User getUserByEmail(String email) {
        Optional<User> User = userRepository.findByEmail(email);
        return User.get();
    }

    public void updateUserRole(Long idUser, UserRole role) {
        User user = getUserById(idUser);
        user.setRole(role);
        userRepository.save(user);
    }

    public void createUser(RegisterDTO registerDTO) {
        var bCryptEncoder = new BCryptPasswordEncoder();
        User newUser = new User();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
        newUser.setPhone(registerDTO.getPhone());
        newUser.setRole(UserRole.CUSTOMER);
        newUser.setCreateAt(new Date());
        newUser.setAddress(registerDTO.getAddress());
        newUser.setFullname(registerDTO.getFullName());
        userRepository.save(newUser);
    }
}
