package com.dev.cinema.security;

import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;
import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private HashUtil hashUtil;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User userFromDB = userService.findByEmail(email);
        if (userFromDB != null) {
            boolean passwordEquals = userFromDB.getPassword()
                    .equals(hashUtil.hashPassword(password, userFromDB.getSalt()));
            if (passwordEquals) {
                return userFromDB;
            }
        }
        throw new AuthenticationException("Incorrect login or password");
    }

    @Override
    public User registration(String email, String password) {
        User user = new User();
        byte[] salt = hashUtil.getSalt();
        user.setSalt(salt);
        user.setEmail(email);
        user.setPassword(hashUtil.hashPassword(password, salt));
        User userFromDb = userService.add(user);
        shoppingCartService.registerCart(userFromDb);
        return userFromDb;
    }
}
