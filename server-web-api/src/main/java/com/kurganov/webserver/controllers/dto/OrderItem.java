package com.kurganov.webserver.controllers.dto;


import com.kurganov.serverdb.entities.Order;
import com.kurganov.serverdb.entities.Product;

import java.io.Serializable;

public class OrderItem implements Serializable {

    private Long id;

    private Long quantity;

    private Double itemPrice;

    private Double totalPrice;

    private Product product;

    private Order order;

    public OrderItem() {
    }

    public OrderItem(Long id, Long quantity, Double itemPrice,
                     Double totalPrice, Product product, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.totalPrice = totalPrice;
        this.product = product;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
