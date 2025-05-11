package com.example.springboot.controller.client;


import com.example.springboot.enums.PropertyStatus;
import com.example.springboot.enums.PropertyType;
import com.example.springboot.repository.PropertyRepository;
import com.example.springboot.repository.entity.Property;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;
    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/home")
    public ModelAndView showPageHome(Principal principal) {
        ModelAndView mav = new ModelAndView("client/home");
        mav.addObject("propertyStatus", PropertyStatus.values());
        mav.addObject("propertyTypes", PropertyType.values());

        List<Property> propertyList = propertyRepository.findAll();
        Map<Long, Boolean> favouriteStatus = new HashMap<>();
        String userId = "";

        if (principal != null) {
            Long loggedInUserId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            if (loggedInUserId != null) {
                for (Property property : propertyList) {
                    favouriteStatus.put(property.getId(), propertyService.isFavourite(property, loggedInUserId));
                }
                userId = loggedInUserId.toString();
            }
        } else {
            for (Property property : propertyList) {
                favouriteStatus.put(property.getId(), false);
            }
        }

        mav.addObject("userId", userId);
        mav.addObject("favouriteStatus", favouriteStatus);
        return mav;
    }

    @GetMapping("/property-{propertyId}")
    public ResponseEntity<Property> getProperty(@PathVariable("propertyId") Long propertyId) {
        Property property = propertyService.getPropertyById(propertyId);
        if (property != null) {
            return ResponseEntity.ok(property);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Property>> searchProperty(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) Long propertyId,
            @RequestParam(required = false) PropertyType type,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String address,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Property> result = propertyService.searchProperty(
                pageable,propertyId,type,minArea,maxArea,minPrice,maxPrice,null,address,null
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/home/property/{propertyId}")
    public ModelAndView showPageProperty(@PathVariable Long propertyId, Principal principal) {
        ModelAndView mav = new ModelAndView("client/property/property-detail");

        Property property = propertyService.getPropertyById(propertyId);
        if (property == null) {
            mav.setViewName("error/404");
            mav.addObject("message", "Không tìm thấy bất động sản.");
            return mav;
        }

        String userId = null;
        if (principal != null) {
            userId = String.valueOf(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        }

        mav.addObject("propertyStatus", PropertyStatus.values());
        mav.addObject("propertyTypes", PropertyType.values());
        mav.addObject("property", property);
        mav.addObject("userId", userId != null ? userId : "");

        return mav;
    }
}
