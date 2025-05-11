package com.example.springboot.controller.admin;


import com.example.springboot.converter.UserDTOConverter;
import com.example.springboot.models.UserDTO;
import com.example.springboot.repository.entity.User;
import com.example.springboot.service.PropertyService;
import com.example.springboot.service.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/assignment_property")
public class AssignmentPropertyController {
    @Autowired
    private UserService userService;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserDTOConverter userDTOConverter;

    @GetMapping("/search")
    public ResponseEntity<Page<UserDTO>> searchUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword
    ){
        Page<UserDTO> userPage = userService.searchUsers(page,size, keyword).map(userDTOConverter :: toUserDTO);
        return ResponseEntity.ok(userPage);
    }

    @PatchMapping("")
    public ResponseEntity<?> updatePropertyUser(
            @RequestParam Long propertyId,
            @RequestParam Long userId
    ) {
        propertyService.assignmentProperty(propertyId, userId);
        return ResponseEntity.ok("Property updated successfully!");
    }
}
