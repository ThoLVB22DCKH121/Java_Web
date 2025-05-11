package com.example.springboot.service.Impl;

import com.example.springboot.converter.DepositDTOConverter;
import com.example.springboot.enums.DepositStatus;
import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.models.DepositDTO;
import com.example.springboot.repository.DepositRepository;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.repository.entity.Deposit;
import com.example.springboot.repository.entity.Property;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DepositServiceImpl implements DepositService {

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepositDTOConverter depositDTOConverter;

    @Override
    public Page<DepositDTO> searchDeposits(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Deposit> depositPage = depositRepository.searchDeposits(keyword, pageable);
        return depositPage.map(depositDTOConverter::toDTO);
    }

    @Override
    public DepositDTO createDeposit(Long propertyId, Long userId, Long depositAmount, String note) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (property.getStatus() == PropertyStatus.SOLD) {
            throw new RuntimeException("Property has been sold");
        }

        Deposit deposit = new Deposit();
        deposit.setUser(user);
        deposit.setProperty(property);
        deposit.setDepositAmount(depositAmount);
        deposit.setNote(note);
        deposit.setCreateAt(new Date());
        deposit.setStatus(DepositStatus.PENDING);

        Deposit savedDeposit = depositRepository.save(deposit);

        return depositDTOConverter.toDTO(savedDeposit);
    }

    @Override
    public void updateDepositStatus(Long depositId, DepositStatus status) {
        Deposit deposit = depositRepository.findById(depositId)
                .orElseThrow(() -> new RuntimeException("Deposit not found"));
        deposit.setStatus(status);
        depositRepository.save(deposit);
    }

    @Override
    public Page<DepositDTO> getUserDepositHistory(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Deposit> depositPage = depositRepository.findByUserId(userId, pageable);
        return depositPage.map(depositDTOConverter::toDTO);
    }
}