package com.dev.cinema.controllers;

import com.dev.cinema.mapper.OrderDtoMapper;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.model.dto.OrderRequestDto;
import com.dev.cinema.model.dto.OrderResponseDto;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final OrderDtoMapper orderDtoMapper;

    public OrderController(UserService userService, OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           OrderDtoMapper orderDtoMapper) {
        this.userService = userService;
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.orderDtoMapper = orderDtoMapper;
    }

    @PostMapping("/complete")
    public void completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        User user = userService.getUserById(orderRequestDto.getUserId());
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        orderService.completeOrder(shoppingCart.getTickets(),user);
    }

    @GetMapping
    public List<OrderResponseDto> getByUserId(Authentication authentication) {
        String email = authentication.getName();
        return orderService
                .getOrderHistory(userService.findByEmail(email))
                .stream()
                .map(orderDtoMapper::orderToDto)
                .collect(Collectors.toList());
    }
}
