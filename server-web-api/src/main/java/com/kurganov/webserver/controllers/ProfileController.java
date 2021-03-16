package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.DeliveryAddress;
import com.kurganov.serverdb.entities.Order;
import com.kurganov.serverdb.entities.OrderItem;
import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.security.AuthUser;
import com.kurganov.webserver.services.DeliveryAddressServiceImpl;
import com.kurganov.webserver.services.OrderServiceImpl;
import com.kurganov.webserver.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
public class ProfileController {

    @Autowired
    private AuthUser authUser;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DeliveryAddressServiceImpl deliveryAddressService;

    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/profile")
    public String profileUser(Model model, HttpServletRequest httpServletRequest) {
        User user = userService.findByUserName(httpServletRequest.getUserPrincipal().getName());
        List<DeliveryAddress> addressList = deliveryAddressService.getUserAddresses(user.getId());
        List<Order> listOrders = orderService.getOrderByUserId(user.getId());
        model.addAttribute("fio", authUser.getCurrentFio());
        model.addAttribute("userName", user);
        model.addAttribute("deliveryAddress", addressList);
        model.addAttribute("listOrders", listOrders);
        return "profile";
    }

    @GetMapping("/profile/myOrder/{id}")
    public String myOrderInfo(Model model, @PathVariable(value = "id") Long id) {
        Order order = orderService.findById(id);
        model.addAttribute("orderModel", order);
        return "order-result";
    }


    @GetMapping("/back")
    public String back() {
        return "redirect:/";
    }
}
