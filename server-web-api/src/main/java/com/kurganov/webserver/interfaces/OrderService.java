package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Order;
import com.kurganov.serverdb.entities.OrderStatus;
import com.kurganov.serverdb.entities.User;
import com.kurganov.webserver.utils.ShoppingCart;

import java.util.List;

public interface OrderService {

    Order makeOrder(ShoppingCart cart, User user);

    List<Order> getAllOrders();

    Order findById(Long id);

    Order saveOrder(Order order);

    Order changeOrderStatus(Order order, OrderStatus newStatus);

    List<Order> getOrderByUserId(Long userId);

    void delete(Long id);

}
