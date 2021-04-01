package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.DeliveryAddress;
import com.kurganov.serverdb.entities.Order;
import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.interfaces.UserService;
import com.kurganov.webserver.security.AuthUser;
import com.kurganov.webserver.services.DeliveryAddressServiceImpl;
import com.kurganov.webserver.services.MailServiceImpl;
import com.kurganov.webserver.services.OrderServiceImpl;
import com.kurganov.webserver.utils.ReceiverApp;
import com.kurganov.webserver.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {
    private UserService userService;
    private OrderServiceImpl orderServiceImpl;
    private DeliveryAddressServiceImpl deliverAddressService;
    private ShoppingCart shoppingCart;
    private ReceiverApp receiverApp;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private MailServiceImpl mailService;

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

    @GetMapping("/orders/list")
    @Secured("ROLE_ADMIN")
    public String ordersList(Model model) {
        List<Order> allOrders = orderServiceImpl.getAllOrders();
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("fio", authUser.getCurrentFio());
        return "orders-list";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/order/{id}")
    public String orderInfo(Model model, @PathVariable(value = "id") Long id) {
        Order order = orderServiceImpl.findById(id);
        model.addAttribute("orderModel", order);
        return "order-result";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        orderServiceImpl.delete(id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/order/fill")
    public String orderFill(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(principal.getName());
        model.addAttribute("cart", shoppingCart);
        List<DeliveryAddress> addressList = deliverAddressService.getUserAddresses(user.getId());
        model.addAttribute("deliveryAddresses", addressList);
        model.addAttribute("fio", authUser.getCurrentFio());
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
        model.addAttribute("fio", authUser.getCurrentFio());
        receiverApp.receiverApp();
        mailService.sendOrderMail(order);
        return "order-before-purchase";
    }

    @GetMapping("/newAddr")
    public String newAddress() {
        return "new-address";
    }

    @PostMapping("/saveAddr")
    public String saveNewAddr(@Valid @ModelAttribute("new-addr") String newAddr, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        deliverAddressService.save(new DeliveryAddress(null, user, newAddr));
        return "redirect:/order/fill";
    }

    // просмотр заказа???
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
        model.addAttribute("fio", authUser.getCurrentFio());
        return "order-result";
    }
}
