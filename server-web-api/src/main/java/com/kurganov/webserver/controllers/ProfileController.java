package com.kurganov.webserver.controllers;

import com.kurganov.webserver.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProfileController {

    @Autowired
    private AuthUser authUser;

    @GetMapping("/profile")
    public String profileUser(Model model) {
        model.addAttribute("fio", authUser.getCurrentFio());
        return "profile";
    }
}
