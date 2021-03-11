package com.kurganov.webserver.security;

import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUser {

    @Autowired
    private UserServiceImpl userService;

    public String getCurrentFio() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByUserName(authentication.getName()).getFirstName() + " "
                + userService.findByUserName(authentication.getName()).getLastName();
    }
}
