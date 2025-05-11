package com.example.springboot.controller.admin;

import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.enums.DepositStatus;
import com.example.springboot.enums.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

    @GetMapping("/admin/property")
    public ModelAndView showAdminPropertyPage(){
        ModelAndView mav = new ModelAndView("admin/property");
        mav.addObject("propertyType", PropertyType.values());
        mav.addObject("propertyStatus", PropertyStatus.values());
        return mav;
    }

    @GetMapping("/admin/assignment_property/{propertyId}")
    public ModelAndView showAdminAssignmentPropertyPage(@PathVariable Long propertyId){
        ModelAndView mav = new ModelAndView("fragments/assign-user");
        mav.addObject("propertyId", propertyId);
        return mav;
    }

    @GetMapping("/admin/user")
    public ModelAndView showAdminUserPage(){
        ModelAndView mav = new ModelAndView("admin/user");
        mav.addObject("userRole", UserRole.values());
        return mav;
    }

    @GetMapping("/admin/deposit")
    public ModelAndView showAdminTransactionPage(){
        ModelAndView mav = new ModelAndView("admin/transaction");
        mav.addObject("depositStatus", DepositStatus.values());
        mav.addObject("propertyStatus", PropertyStatus.values());
        return mav;
    }
}
