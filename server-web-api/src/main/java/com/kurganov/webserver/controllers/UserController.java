package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.config.FilterApp;
import com.kurganov.webserver.security.AuthUser;
import com.kurganov.webserver.services.RoleServiceImpl;
import com.kurganov.webserver.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/users")
public class UserController {

    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 5;

    private UserServiceImpl userServiceImpl;
    private RoleServiceImpl roleServiceImpl;
    private FilterApp filterApp;

    @Autowired
    private AuthUser authUser;

    @Autowired
    public void setUsersService(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.roleServiceImpl = roleServiceImpl;
    }

    @Autowired
    public void setFilterApp(FilterApp filterApp) {
        this.filterApp = filterApp;
    }

    @GetMapping
    public String showUsers(Model model, @RequestParam(value = "page") Optional<Integer> page,
                            @RequestParam(value = "word", required = false) Optional<String> word) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<User> users = filterApp.filterAppUsers(word, currentPage, PAGE_SIZE);

        model.addAttribute("totalPage", users.getTotalPages());
        model.addAttribute("page", currentPage);
        model.addAttribute("users", users.getContent());
        model.addAttribute("fio", authUser.getCurrentFio());
        word.ifPresent(s -> model.addAttribute("word", s));
        return "users-list";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) throws Exception {
        User user = userServiceImpl.findById(id).orElseThrow(Exception::new);
        if (user == null) {
            user = new User();
        }
        model.addAttribute("userTest", user);
        model.addAttribute("roles", roleServiceImpl.findAll());
        model.addAttribute("fio", authUser.getCurrentFio());
        return "edit-user";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                             HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "edit-user";
        }
        userServiceImpl.saveUser(user);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        userServiceImpl.deleteById(id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

}
