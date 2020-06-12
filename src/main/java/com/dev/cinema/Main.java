package com.dev.cinema;

import com.dev.cinema.config.AppConfig;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDateTime;
import java.time.Month;
import javax.naming.AuthenticationException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws AuthenticationException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        Movie movie = new Movie();
        movie.setTitle("Terminator 2");
        MovieService movieService = context.getBean(MovieService.class);
        movie = movieService.add(movie);

        movieService.getAll().forEach(System.out::println);

        CinemaHall blueHall = new CinemaHall();
        blueHall.setCapacity(50);
        blueHall.setDescription("Premium hall");
        CinemaHall greenHall = new CinemaHall();
        greenHall.setCapacity(400);
        greenHall.setDescription("Students Only");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);
        cinemaHallService.add(blueHall);
        cinemaHallService.add(greenHall);
        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(greenHall);
        movieSession.setMovie(movie);
        movieSession.setSessionTime(LocalDateTime.of(2020, Month.MAY, 30, 15, 30));

        MovieSessionService movieSessionService =
                context.getBean(MovieSessionService.class);
        movieSessionService.add(movieSession);

        UserService userService = context.getBean(UserService.class);

        AuthenticationService registration =
                context.getBean(AuthenticationService.class);
        User john = registration.registration("John123@ll.com", "1");
        registration.login("John123@ll.com", "1");
        ShoppingCartService shoppingCartService =
                context.getBean(ShoppingCartService.class);

        OrderService orderService =
                context.getBean(OrderService.class);
        ShoppingCart cart = shoppingCartService.getByUser(john);
        orderService.completeOrder(cart.getTickets(), john);
        shoppingCartService.clear(cart);
        shoppingCartService.addSession(movieSession, john);
        orderService.completeOrder(cart.getTickets(), john);
        shoppingCartService.clear(cart);

        System.out.println("ORDER HISTORY");

        orderService.getOrderHistory(john)
                .forEach(order -> System.out.println(order.toString()));
    }
}
