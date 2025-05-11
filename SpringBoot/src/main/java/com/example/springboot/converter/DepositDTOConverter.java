package com.example.springboot.converter;

import com.example.springboot.models.DepositDTO;
import com.example.springboot.repository.entity.Deposit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DepositDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public DepositDTO toDTO(Deposit deposit) {
        DepositDTO depositDTO = modelMapper.map(deposit, DepositDTO.class);
        depositDTO.setPropertyId(deposit.getProperty().getId());
        depositDTO.setUserId(deposit.getUser().getId());
        depositDTO.setPhone(deposit.getUser().getPhone());
        depositDTO.setFullName(deposit.getUser().getFullname());
        return depositDTO;
    }

    public List<DepositDTO> toDTOList(List<Deposit> deposits) {
        return deposits.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Deposit toEntity(DepositDTO depositDTO, Deposit deposit) {
        deposit.setId(depositDTO.getId());
        deposit.setDepositAmount(depositDTO.getDepositAmount());
        deposit.setNote(depositDTO.getNote());
        deposit.setCreateAt(depositDTO.getCreateAt());
        deposit.setStatus(depositDTO.getStatus());
        return deposit;
    }
}