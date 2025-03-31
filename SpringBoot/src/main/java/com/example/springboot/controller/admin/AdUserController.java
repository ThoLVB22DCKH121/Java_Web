package com.example.springboot.controller.admin;

import com.example.springboot.converter.UserDTOConverter;
import com.example.springboot.enums.UserRole;
import com.example.springboot.models.UserDTO;
import com.example.springboot.repository.entity.AppUser;
import com.example.springboot.service.Impl.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/user")
public class AdUserController {
    @Autowired
    AppUserService appUserService;
    @Autowired
    UserDTOConverter userDTOConverter;

    @GetMapping()
    public ModelAndView showAdminUserPage(){
        ModelAndView mav = new ModelAndView("admin/user");
        List<AppUser> userEntityList = appUserService.getAllUsers();
        List<UserDTO> userDTOList = userDTOConverter.toUserDTOList(userEntityList);
        mav.addObject("userDTOList", userDTOList);
        mav.addObject("userDTO", new UserDTO());
        mav.addObject("userRole", UserRole.values());
        return mav;
    }

    @PutMapping("/{idUser}")
    public ModelAndView updateRoleUser(@PathVariable Integer idUser, @ModelAttribute UserDTO userDTO){
        appUserService.updateUser(idUser,userDTOConverter.toEntity(userDTO,new AppUser()));
        return new ModelAndView("redirect:/admin/user");
    }
}
