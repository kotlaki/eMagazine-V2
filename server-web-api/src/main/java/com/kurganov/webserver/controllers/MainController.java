package com.kurganov.webserver.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/info")
    public String showInfoPage(Model model) {
        return "info";
    }

}
