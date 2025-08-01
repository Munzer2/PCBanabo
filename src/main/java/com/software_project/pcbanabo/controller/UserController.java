package com.software_project.pcbanabo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.dto.UserDto;
import com.software_project.pcbanabo.model.UserInfo;
import com.software_project.pcbanabo.service.UserInfoService;

@RestController
@RequestMapping("/api/users")
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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserInfo> users = userService.getAllUsers();
        
        List<UserDto> userDtos = users.stream()
            .map(user -> {
                UserDto dto = new UserDto();
                dto.setId(user.getId());
                dto.setUserName(user.getUsername());
                dto.setEmail(user.getEmail());
                dto.setUserType("USER");
                return dto;
            })
            .collect(Collectors.toList());

        return ResponseEntity.ok(userDtos);
    }
}
