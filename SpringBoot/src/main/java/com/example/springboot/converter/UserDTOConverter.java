package com.example.springboot.converter;

import com.example.springboot.models.UserDTO;
import com.example.springboot.repository.entity.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toUserDTO(AppUser appUser) {
        UserDTO userDTO = modelMapper.map(appUser, UserDTO.class);
        userDTO.setName(appUser.getLastname()+" "+appUser.getFirstname());
        return userDTO;
    }

    public List<UserDTO> toUserDTOList(List<AppUser> users) {
        return users.stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }

    public AppUser toEntity(UserDTO userDTO, AppUser appUser) {
        modelMapper.map(userDTO, appUser);
        return appUser;
    }
}
