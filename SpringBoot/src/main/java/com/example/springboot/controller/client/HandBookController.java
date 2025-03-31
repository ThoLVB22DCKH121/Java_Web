package com.example.springboot.controller.client;

import com.example.springboot.repository.entity.HandBookEntity;
import com.example.springboot.service.HandBookService;
import com.example.springboot.service.Impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HandBookController {
    @Autowired
    private HandBookService handBookService;
    @GetMapping("/handbook")
    public ModelAndView handBook(){
        ModelAndView mav = new ModelAndView("client/handbook/handbook-list");
        List<HandBookEntity> handBookList = handBookService.getAllHandBooks();
        List<String> categories = handBookList.stream().map(HandBookEntity::getCategory).collect(Collectors.toList());
        String username=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        mav.addObject("username",username);
        mav.addObject("categories", categories);
        mav.addObject("handBookList", handBookList);
        mav.addObject("currentPage", "handbook");
        return mav;
    }

    @GetMapping("/handbook/{idHandBook}")
    public ModelAndView handBookDetail(@PathVariable("idHandBook") Long idHandBook){
        ModelAndView mav = new ModelAndView("client/handbook/handbook-detail");
        mav.addObject("handBook", handBookService.getHandBookById(idHandBook));
        return mav;
    }
}
