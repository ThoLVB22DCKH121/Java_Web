package com.example.springboot.controller.client;

import com.example.springboot.repository.entity.Property;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserDetailsImpl;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class FavouritesC {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @GetMapping("/favorites")
    public ModelAndView favoritesList() {
        ModelAndView mav = new ModelAndView("client/account/favorites_list");
        Long userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userService.getUserById(userId);
        Set<Property> propertyList = user.getFavouriteProperties();
        Map<Long, Boolean> favouriteStatus = new HashMap<>();
        if (userId != null) {
            for (Property property : propertyList) {
                favouriteStatus.put(property.getId(), propertyService.isFavourite(property, userId));
            }
        }
        mav.addObject("userId", userId);
        mav.addObject("favouriteStatus", favouriteStatus);
        return mav;
    }

    @GetMapping("/favorites_list")
    public ResponseEntity<List<Map<String, Object>>> getFavoritesList() {
        Long userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userService.getUserById(userId);
        Set<Property> propertyList = user.getFavouriteProperties();

        List<Map<String, Object>> response = propertyList.stream().map(property -> {
            Map<String, Object> propertyMap = new HashMap<>();
            propertyMap.put("id", property.getId());
            propertyMap.put("name", property.getName());
            propertyMap.put("price", property.getPrice());
            propertyMap.put("area", property.getArea());
            propertyMap.put("address", property.getAddress());
            propertyMap.put("status", property.getStatus());
            propertyMap.put("imageList", property.getImageList());
            propertyMap.put("isFavourite", propertyService.isFavourite(property, userId));
            return propertyMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/favourites/{propertyId}/{userId}")
    public ResponseEntity<?> addFavorites(@PathVariable Long propertyId, @PathVariable Long userId) {
        propertyService.addFavourite(propertyId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/favourites/{propertyId}/{userId}")
    public ResponseEntity<?> removeFavorites(@PathVariable Long propertyId, @PathVariable Long userId) {
        propertyService.removeFavourite(propertyId, userId);
        return ResponseEntity.noContent().build();
    }
}
