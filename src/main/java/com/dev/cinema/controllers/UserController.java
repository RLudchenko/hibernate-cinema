package com.dev.cinema.controllers;

import com.dev.cinema.mapper.UserDtoMapper;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.UserResponseDto;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    public UserController(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping("/by-email")
    public UserResponseDto getByEmail(Authentication authentication) {
        String emailLogin = authentication.getName();
        User user = userService.getByEmail(emailLogin);

        return userDtoMapper.userToDto(user);
    }
}
