package com.example.springboot.controller.client;


import com.example.springboot.service.Impl.UserDetailsImpl;
import com.example.springboot.service.NewsService;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private NewsService newsService;
    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("client/home");
        String username=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        mav.addObject("username",username);
        mav.addObject("projectList", projectService.getAllProjects());
        mav.addObject("propertyList", propertyService.getAllProperties());
        mav.addObject("newsList", newsService.getAllNews());
        mav.addObject("currentPage", "home");
        return mav;
    }
}
