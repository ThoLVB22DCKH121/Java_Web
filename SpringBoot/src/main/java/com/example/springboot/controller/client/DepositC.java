package com.example.springboot.controller.client;

import com.example.springboot.models.DepositDTO;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.DepositService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepositC {
    @Autowired
    private DepositService depositService;

    @Autowired
    private UserService userService;

    @GetMapping("/history")
    public ModelAndView transactions() {
        ModelAndView mav = new ModelAndView("client/account/history_transaction");
        Long userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        mav.addObject("userId", userId);
        return mav;
    }

    @GetMapping("/history_deposit")
    public ResponseEntity<Page<DepositDTO>> getUserDepositHistory(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<DepositDTO> depositHistory = depositService.getUserDepositHistory(userId, page, size);
        return ResponseEntity.ok(depositHistory);
    }

    @PostMapping("/create_deposit")
    public ResponseEntity<?> createDeposit(
            @RequestParam Long propertyId,
            @RequestParam Long userId,
            @RequestParam Long depositAmount,
            @RequestParam(required = false) String note
    ) {
        DepositDTO deposit = depositService.createDeposit(
                propertyId,
                userId,
                depositAmount,
                note
        );
        return ResponseEntity.ok(deposit);
    }
}
