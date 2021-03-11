package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.controllers.dto.SystemUserDTO;
import com.kurganov.webserver.interfaces.UserService;
import com.kurganov.webserver.security.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    private AuthUser authUser;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUserDTO());
        model.addAttribute("fio", authUser.getCurrentFio());
        return "registration-form";
    }

    // Binding Result после @ValidModel !!!
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUserDTO theSystemUserDTO,
                                          BindingResult theBindingResult, Model model) {
        String userName = theSystemUserDTO.getUserName();
        logger.debug("Processing registration form for: " + userName);
        if (theBindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            // theSystemUser.setUserName(null);
            model.addAttribute("systemUser", theSystemUserDTO);
            model.addAttribute("registrationError", "User with current username already exists");
            model.addAttribute("fio", authUser.getCurrentFio());
            logger.debug("User name already exists.");
            return "registration-form";
        }
        userService.save(theSystemUserDTO);
        logger.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }
}
