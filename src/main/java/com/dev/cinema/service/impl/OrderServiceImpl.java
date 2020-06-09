package com.dev.cinema.service.impl;

import com.dev.cinema.dao.OrderDao;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.Ticket;
import com.dev.cinema.model.User;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setTickets(tickets);
        order.setOrderDate(LocalDateTime.now());
        Order orderFromDb = orderDao.add(order);
        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        shoppingCartService.clear(shoppingCart);
        return orderFromDb;
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getOrderHistory(user);
    }
}
