package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.Order;
import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.interfaces.UserService;
import com.kurganov.webserver.services.DeliveryAddressServiceImpl;
import com.kurganov.webserver.services.OrderServiceImpl;
import com.kurganov.webserver.utils.ReceiverApp;
import com.kurganov.webserver.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class OrderController {
    private UserService userService;
    private OrderServiceImpl orderServiceImpl;
    private DeliveryAddressServiceImpl deliverAddressService;
    private ShoppingCart shoppingCart;
    private ReceiverApp receiverApp;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @Autowired
    public void setDeliverAddressService(DeliveryAddressServiceImpl deliverAddressService) {
        this.deliverAddressService = deliverAddressService;
    }

    @Autowired
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Autowired
    public void setReceiverApp(ReceiverApp receiverApp) {
        this.receiverApp = receiverApp;
    }

    @GetMapping("/order/fill")
    public String orderFill(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("deliveryAddresses", deliverAddressService.getUserAddresses(user.getId()));
        return "order-filler";
    }

    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, Principal principal, @RequestParam("phoneNumber") String phoneNumber,
                               @RequestParam("deliveryAddress") Long deliveryAddressId) throws Exception {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        Order order = orderServiceImpl.makeOrder(shoppingCart, user);
        order.setDeliveryAddress(deliverAddressService.getUserAddressById(deliveryAddressId));
        order.setPhoneNumber(phoneNumber);
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(0.0);
        order = orderServiceImpl.saveOrder(order);
        model.addAttribute("order", order);
        receiverApp.receiverApp();
        return "order-before-purchase";
    }

    @GetMapping("/order/result/{id}")
    public String orderConfirm(Model model, @PathVariable(name = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderServiceImpl.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
}
