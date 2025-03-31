package com.example.springboot.controller.client;

import com.example.springboot.enums.UserRole;
import com.example.springboot.repository.entity.AppUser;
import com.example.springboot.models.RegisterDTO;
import com.example.springboot.repository.AppUserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class AuthController {
    @Autowired
    private AppUserRepository appUserRepository;

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/project";
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
        AppUser appUser = appUserRepository.findByUsername(registerDTO.getUsername());
        if(appUser != null) {
            bindingResult.addError(
                    new FieldError("registerDTO", "username", "UserName is already in use")
            );
        }
        if(bindingResult.hasErrors()) {
            return "client/account/register";
        }

        try{
            var bCryptEncoder = new BCryptPasswordEncoder();
            AppUser newUser = new AppUser();
            newUser.setUsername(registerDTO.getUsername());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            newUser.setPhone(registerDTO.getPhone());
            newUser.setRole(UserRole.CUSTOMER);
            newUser.setCreateAt(new Date());
            newUser.setAddress(registerDTO.getAddress());
            newUser.setFirstname(registerDTO.getFirstName());
            newUser.setLastname(registerDTO.getLastName());
            appUserRepository.save(newUser);

            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);
        }
        catch(Exception e) {
            bindingResult.addError(
                    new FieldError("registerDTO", "firstName", e.getMessage())
            );
        }
        return "client/account/register";
    }

}
