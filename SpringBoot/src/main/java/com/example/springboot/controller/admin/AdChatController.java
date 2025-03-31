package com.example.springboot.controller.admin;

import com.example.springboot.repository.entity.AppUser;
import com.example.springboot.service.Impl.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/chat")
public class AdChatController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping
    public ModelAndView showAdminChatPage() {
        ModelAndView mav = new ModelAndView("admin/chat");
        List<AppUser> users = appUserService.getAllUsers()
                .stream()
                .filter(user -> !user.getUsername().equals("admin"))
                .collect(Collectors.toList());
        mav.addObject("users", users);
        System.out.println(users.size());
        return mav;
    }
}