package com.example.springboot.controller.client;

import com.example.springboot.enums.ProjectStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.ProjectImageEntity;
import com.example.springboot.service.Impl.UserDetailsImpl;
import com.example.springboot.service.ProjectImageService;
import com.example.springboot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectImageService projectImageService;

    @GetMapping("/projects")
    public ModelAndView projects(
            @RequestParam(name = "keyword", required = false) String keyword,
//            @RequestParam(name = "location", required = false) Location location,
            @RequestParam(name = "type", required = false) PropertyType type,
            @RequestParam(name = "status", required = false) ProjectStatus status
    ){
        ModelAndView mav = new ModelAndView("client/projects/project-list");
        String username=((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        mav.addObject("username",username);
        mav.addObject("projectEntity",new ProjectEntity());
        mav.addObject("projectList",projectService.getAllProjects());
        mav.addObject("keyword", keyword);
//        mav.addObject("location", location);
        mav.addObject("type", type);
        mav.addObject("status", status);
//        mav.addObject("locations", Location.values());
        mav.addObject("propertyTypes", PropertyType.values());
        mav.addObject("projectStatuses", ProjectStatus.values());
        mav.addObject("currentPage", "projects");
        return mav;
    }

    @GetMapping("/projects/{idProject}")
    public ModelAndView projectDetail(@PathVariable Long idProject){
        ModelAndView mav = new ModelAndView("client/projects/project-detail");
        mav.addObject("projectEntity",projectService.getProjectById(idProject));
        mav.addObject("projectImageEntity",new ProjectImageEntity());
        mav.addObject("projectImageList",projectImageService.getAllProjectImages());
        return mav;
    }
}
