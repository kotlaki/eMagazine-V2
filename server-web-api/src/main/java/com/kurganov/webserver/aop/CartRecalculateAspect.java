package com.kurganov.webserver.aop;

import com.kurganov.serverdb.entities.OrderItem;
import com.kurganov.webserver.utils.ShoppingCart;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class CartRecalculateAspect {

    @Autowired
    private ShoppingCart shoppingCart;

    @AfterReturning(
            pointcut = "execution(public * com.kurganov.webserver.controllers.ShoppingCartController.addProductToCart(..)) " +
                    "|| execution(public * com.kurganov.webserver.controllers.ShoppingCartController.delete(..))",
            returning = "result")
    public void afterRecalculate(JoinPoint jp, String result) {
        shoppingCart.setTotalCost(recalculate());
    }

    private Double recalculate() {
        List<OrderItem> items = shoppingCart.getItems();
        Double totalCost = 0.0;
        for (OrderItem o : items) {
            o.setTotalPrice(o.getQuantity() * o.getProduct().getPrice());
            totalCost += o.getTotalPrice();
        }
        return totalCost;
    }
}
