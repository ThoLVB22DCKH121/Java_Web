package com.example.springboot.controller.client;

import com.example.springboot.models.DepositDTO;
import com.example.springboot.repository.ConfirmOtpRepository;
import com.example.springboot.repository.entity.confirmOTP;
import com.example.springboot.repository.entity.Property;
import com.example.springboot.repository.entity.User;
import com.example.springboot.models.RegisterDTO;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.DepositService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmOtpRepository otpRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositService transactionService;

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/property";
        }
        else if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"))) {
            return "redirect:/admin/transaction";
        }
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "client/account/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute(registerDTO);
        model.addAttribute("success", false);
        return "client/account/register";
    }

    @PostMapping("/register")
    public String register(
            Model model,
            @Valid @ModelAttribute RegisterDTO registerDTO,
            BindingResult bindingResult
    ) {
        if(!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            bindingResult.addError(
                    new FieldError("registerDTO", "confirmPassword", "Passwords and Confirm Passwords do not match")
            );
        }
        UserDetailsImpl user = userService.loadUserByUsername(registerDTO.getUsername());

        if(user != null) {
            bindingResult.addError(
                    new FieldError("registerDTO", "username", "UserName is already in use")
            );
        }
        if(bindingResult.hasErrors()) {
            return "client/account/register";
        }

        try{
            userService.createUser(registerDTO);
            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
        }
        catch(Exception e) {
            bindingResult.addError(
                    new FieldError("registerDTO", "fullName", e.getMessage())
            );
        }
        return "client/account/register";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "fragments/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return "redirect:/forgot-password?error";
        }
        confirmOTP otp = new confirmOTP(generateOtp(),user,new Date());
        otpRepository.save(otp);
        sendOtpEmail(email,otp.getOtp());
        return "redirect:/reset-password?email=" + email;
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "fragments/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("email") String email,
                                       @RequestParam("otp") String otp,
                                       @RequestParam("password") String password) {
        confirmOTP passwordResetOtp = otpRepository.findByOtp(otp);
        if(passwordResetOtp == null) {
            return "redirect:/reset-password?error";
        }
        System.out.println(email + " " + otp + " " + password);
        User user = userService.getUserByEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        otpRepository.delete(passwordResetOtp);
        return "redirect:/login?reset-success";
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("OTP Đặt Lại Mật Khẩu");
        message.setText("Mã OTP của bạn là: " + otp);
        mailSender.send(message);
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
       ModelAndView mav = new ModelAndView("client/account/profile");
       Long userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
       mav.addObject("userId", userId);
       return mav;
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
                                           @RequestBody User user) {
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }

        boolean updated = userService.updateUser(userId, user);
        if (updated) {
            return ResponseEntity.ok("Profile updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

}
