package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.Order;
import com.kurganov.webserver.security.AuthUser;
import com.kurganov.webserver.services.MailServiceImpl;
import com.kurganov.webserver.services.OrderServiceImpl;
import com.kurganov.webserver.utils.ShoppingCart;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/paypal")
public class PayPalController {
    private String clientId = "AXJTs_qsXdtx9ycPrNVk6rLPIM0OWhKloXo6qWlIZ5BYdMD1ffGsmEqxrUYkN6A0_3zvQwXYFaXknL1j";
    private String clientSecret = "EHtyfRERiXy8pGBf5137ky3fLiTAsKeH35D-4eC3UNQ-ob0qOnYOQ6GF5g3-r11nMzwO4Jjlbf_s7xxs";
    private String mode = "sandbox";

    @Autowired
    private ShoppingCart shoppingCart;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private OrderServiceImpl orderService;

    private APIContext apiContext = new APIContext(clientId, clientSecret, mode);

    @RequestMapping("/buy")
    public String buy(HttpServletRequest request, HttpServletResponse response, Model model) throws PayPalRESTException {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8188");
        redirectUrls.setReturnUrl("http://localhost:8188/paypal/success");

        Amount amount = new Amount();
        amount.setCurrency("RUB");
        amount.setTotal("1.0");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Покупка в eMagazine");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);
        payment.setIntent("sale");

        Payment doPayment = payment.create(apiContext);

        Iterator<Links> links = doPayment.getLinks().iterator();

        while (links.hasNext()) {
            Links link = links.next();
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                model.addAttribute("fio", authUser.getCurrentFio());
                return "redirect:" + link.getHref();
            }
        }
        model.addAttribute("fio", authUser.getCurrentFio());
        return "result";
    }

    @GetMapping("/success")
    public String success(HttpServletRequest request, HttpServletResponse response, Model model) throws PayPalRESTException {
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

//        if (payerId.isEmpty() || payerId == null || paymentId.isEmpty() || paymentId == null) {
//            return "redirect:/";
//        }

        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment executorPayment = payment.execute(apiContext, paymentExecution);

        if (executorPayment.getState().equals("approved")) {
            model.addAttribute("message", "Ваш заказ сформерован");
            model.addAttribute("fio", authUser.getCurrentFio());

            mailService.sendMail("dev.kurganov@gmail.com", "New Order", "Оплачен новый заказ!");
            // удаляем содержимое корзины
            shoppingCart.removeAll(shoppingCart.getItems());
        } else {
            model.addAttribute("message", "Ошибка при формировании заказа");
            model.addAttribute("fio", authUser.getCurrentFio());
        }

        return "page-success";
    }

}
