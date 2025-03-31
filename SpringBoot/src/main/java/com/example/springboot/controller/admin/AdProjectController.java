package com.example.springboot.controller.admin;

import com.example.springboot.enums.ProjectStatus;
import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.entity.ProjectEntity;
import com.example.springboot.repository.entity.PropertyEntity;
import com.example.springboot.service.LocationService;
import com.example.springboot.service.ProjectService;
import com.example.springboot.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/project")
public class AdProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private LocationService locationService;
    @GetMapping()
    public ModelAndView showAdminProjectPage() {
        ModelAndView mav = new ModelAndView("admin/project");
        mav.addObject("projectEntity", new ProjectEntity());
        mav.addObject("projectList",projectService.getAllProjects());
        mav.addObject("projectStatuses", ProjectStatus.values());
        mav.addObject("propertyList",propertyService.getAllProperties());
        mav.addObject("propertyEntity", new PropertyEntity());
        mav.addObject("propertyStatuses", PropertyStatus.values());
        mav.addObject("propertyTypes", PropertyType.values());
        mav.addObject("success",false);
        mav.addObject("error",false);
        mav.addObject("provinceList",locationService.findAllProvinces());
        return mav;
    }

    @PostMapping()
    public ModelAndView saveProject(@ModelAttribute("projectEntity") ProjectEntity projectEntity) {
        ModelAndView mav = new ModelAndView("redirect:/admin/project");
        projectService.createProject(projectEntity);
        mav.addObject("success", "Thêm dự án thành công!");
        return mav;
    }

    @DeleteMapping("/{idProject}")
    public ModelAndView deleteProject(@PathVariable("idProject") Long id) {
        projectService.deleteProject(id);
        return new ModelAndView("redirect:/admin/project");
    }

    @PutMapping("/{idProject}")
    public ModelAndView updateProject(@PathVariable("idProject") Long id,@ModelAttribute ProjectEntity projectEntity,@RequestParam String wardCode) {
        projectService.updateProject(id,projectEntity,wardCode);
        return new ModelAndView("redirect:/admin/project");
    }

    @PostMapping("/{idProject}")
    public ModelAndView savePropertyForProject(@PathVariable("idProject") Long idProject,@ModelAttribute PropertyEntity propertyEntity) {
        ModelAndView mav = new ModelAndView("redirect:/admin/project");
        propertyService.createPropertyForProject(idProject,propertyEntity);
        mav.addObject("success", "Thêm BDS thành công!");
        return mav;
    }

    @DeleteMapping("/property/{idProperty}")
    public ModelAndView deletePropertyOfProject(@PathVariable("idProperty") Long idProperty) {
        propertyService.deleteProperty(idProperty);
        return new ModelAndView("redirect:/admin/project");
    }

    @PutMapping("/property/{idProperty}")
    public ModelAndView updateProperty(@PathVariable("idProperty") Long idProperty,
                                       @ModelAttribute PropertyEntity propertyEntity) {
        propertyService.updateProperty(idProperty,propertyEntity);
        return new ModelAndView("redirect:/admin/project");
    }
}


