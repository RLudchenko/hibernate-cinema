package com.dev.cinema.security;

import com.dev.cinema.lib.Inject;
import com.dev.cinema.lib.Service;
import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;
import javax.naming.AuthenticationException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User userFromDB = userService.findByEmail(email).orElseThrow(() ->
                new AuthenticationException("Incorrect username or password!"));
        if (HashUtil.hashPassword(password, userFromDB.getSalt())
                .equals(userFromDB.getPassword())) {
            return userFromDB;
        }
        throw new AuthenticationException("Incorrect username or password!");
    }

    @Override
    public User registration(String email, String password) {
        User user = new User();
        byte[] salt = HashUtil.getSalt();
        user.setSalt(salt);
        user.setEmail(email);
        user.setPassword(HashUtil.hashPassword(password, salt));
        return userService.add(user);
    }
}
