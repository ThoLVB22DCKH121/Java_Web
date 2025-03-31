package com.example.springboot.controller.admin;

import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.ProjectImageEntity;
import com.example.springboot.repository.entity.PropertyEntity;
import com.example.springboot.repository.entity.PropertyImageEntity;
import com.example.springboot.service.ProjectImageService;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.PropertyImageService;
import com.example.springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/image")
public class AdImageController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectImageService projectImageService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private PropertyImageService propertyImageService;

    @GetMapping
    public ModelAndView showAdminImagePage() {
        ModelAndView mav = new ModelAndView("admin/image");
        mav.addObject("projectList", projectService.getAllProjects());
        mav.addObject("projectImageList", projectImageService.getAllProjectImages());
        mav.addObject("projectEntity", new ProjectEntity());
        mav.addObject("projectImageEntity", new ProjectImageEntity());
        mav.addObject("propertyList", propertyService.getAllProperties());
        mav.addObject("propertyImageList", propertyImageService.getAllPropertyImages());
        mav.addObject("propertyEntity", new PropertyEntity());
        mav.addObject("propertyImageEntity", new PropertyImageEntity());
        return mav;
    }

    @PutMapping("/project/{idProject}")
    public ModelAndView saveProjectImage(@PathVariable("idProject") Long idProject,
                                         @ModelAttribute ProjectImageEntity projectImageEntity

    ) {
        projectImageService.createProjectImage(idProject,projectImageEntity);
        return new ModelAndView("redirect:/admin/image");
    }

    @DeleteMapping("/project/{idProjectImage}")
    public ModelAndView deleteImageOfProject(@PathVariable("idProjectImage") Long idImage) {
        projectImageService.deleteImage(idImage);
        return new ModelAndView("redirect:/admin/image");
    }

    @PutMapping("/property/{idProperty}")
    public ModelAndView savePropertyImage(@PathVariable("idProperty") Long idProperty,
                                          @ModelAttribute PropertyImageEntity propertyImageEntity) {
        propertyImageService.createPropertyImage(idProperty,propertyImageEntity);
        return new ModelAndView("redirect:/admin/image");
    }

    @DeleteMapping("/property/{idPropertyImage}")
    public ModelAndView deleteImageOfProperty(@PathVariable("idPropertyImage") Long idImage) {
        propertyImageService.deleteImage(idImage);
        return new ModelAndView("redirect:/admin/image");
    }
}
