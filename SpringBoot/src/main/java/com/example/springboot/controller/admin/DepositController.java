package com.example.springboot.controller.admin;

import com.example.springboot.enums.DepositStatus;
import com.example.springboot.models.DepositDTO;
import com.example.springboot.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/deposit")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @GetMapping("/search")
    public ResponseEntity<Page<DepositDTO>> searchDeposits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword
    ) {
        Page<DepositDTO> depositPage = depositService.searchDeposits(page, size, keyword);
        return ResponseEntity.ok(depositPage);
    }

    @PatchMapping("/{depositId}")
    public ResponseEntity<?> updateDepositStatus(
            @PathVariable Long depositId,
            @RequestParam DepositStatus status
    ) {
        depositService.updateDepositStatus(depositId, status);
        return ResponseEntity.ok("Deposit status updated successfully");
    }
}