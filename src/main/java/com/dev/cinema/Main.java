package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.UserService;
import javax.naming.AuthenticationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("com.dev.cinema");
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws AuthenticationException {
        MovieService movieService = (MovieService) INJECTOR.getInstance(MovieService.class);
        UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

        Movie movie = new Movie();
        movie.setTitle("Terminator 2");
        movie = movieService.add(movie);

        movieService.getAll().forEach(System.out::println);

        var blueHall = new CinemaHall();
        blueHall.setCapacity(50);
        blueHall.setDescription("Premium hall");
        var greenHall = new CinemaHall();
        greenHall.setCapacity(400);
        greenHall.setDescription("Students Only");
        var cinemaHallService =
                (CinemaHallService) INJECTOR.getInstance(CinemaHallService.class);
        cinemaHallService.add(blueHall);
        cinemaHallService.add(greenHall);

        User john = new User();

        AuthenticationService registration =
                (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);
        registration.registration("John123@ll.com", "1");
        registration.login("John123@ll.com", "1");
    }
}
