package com.dev.cinema.mapper;

import com.dev.cinema.model.Order;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.dto.OrderResponseDto;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public OrderResponseDto orderToDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setId(order.getId());
        orderResponseDto.setTicketId(
                order.getTickets()
                .stream()
                .map(Ticket::getId)
                .collect(Collectors.toList()));

        orderResponseDto.setUserId(order.getUser().getId());
        orderResponseDto.setOrderDate(order.getOrderDate());

        return orderResponseDto;
    }
}
