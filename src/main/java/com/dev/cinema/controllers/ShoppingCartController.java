package com.dev.cinema.controllers;

import com.dev.cinema.mapper.ShoppingCartMapper;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.ShoppingCartRequestDto;
import com.dev.cinema.model.dto.ShoppingCartResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final MovieSessionService movieSessionService;
    private final ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartController(UserService userService,
                                  ShoppingCartService shoppingCartService,
                                  MovieSessionService movieSessionService,
                                  ShoppingCartMapper shoppingCartMapper) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.movieSessionService = movieSessionService;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @PostMapping("/add-movie-session")
    public void addMovieSessionToUser(
            @RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        MovieSession movieSession = movieSessionService
                .getMovieSessionById(shoppingCartRequestDto.getMovieSessionId());
        User user = userService.getUserById(shoppingCartRequestDto.getUserId());
        shoppingCartService.addSession(movieSession, user);
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUserId(Authentication authentication) {
        String name = authentication.getName();
        User byEmail = userService.findByEmail(name);
        return shoppingCartMapper.cartToDto(shoppingCartService
                .getByUser(userService.getUserById(byEmail.getId())));
    }
}
