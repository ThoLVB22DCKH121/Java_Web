package com.example.springboot.service;

import com.example.springboot.enums.DepositStatus;
import com.example.springboot.models.DepositDTO;
import com.example.springboot.repository.entity.Deposit;
import org.springframework.data.domain.Page;

public interface DepositService {
    public DepositDTO createDeposit(Long propertyId, Long userId, Long depositAmount, String note);
    public void updateDepositStatus(Long depositId, DepositStatus status);
    public Page<DepositDTO> searchDeposits(int page, int size, String keyword);
    public Page<DepositDTO> getUserDepositHistory(Long userId, int page, int size);
}
