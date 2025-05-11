package com.example.springboot.converter;

import com.example.springboot.models.UserDTO;
import com.example.springboot.repository.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toUserDTO(User userEntity) {
        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        return userDTO;
    }

    public List<UserDTO> toUserDTOList(List<User> users) {
        return users.stream()
                .map(this::toUserDTO)
                .collect(Collectors.toList());
    }

    public User toEntity(UserDTO userDTO, User userEntity) {
        modelMapper.map(userDTO, userEntity);
        return userEntity;
    }
}
