package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.dto.UserDto;
import com.software_project.pcbanabo.model.UserInfo;
import com.software_project.pcbanabo.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserInfoService userService;

    public UserController(UserInfoService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        UserInfo ui = userService.getById(id);

        UserDto dto = new UserDto();
        dto.setId(ui.getId());
        dto.setUserName(ui.getUsername());
        dto.setEmail(ui.getEmail());
        dto.setUserType("USER");

        return ResponseEntity.ok(dto);
    }
}
