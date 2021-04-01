package com.kurganov.webserver.controllers.dto;



import com.kurganov.serverdb.entities.DeliveryAddress;
import com.kurganov.serverdb.entities.OrderStatus;
import com.kurganov.serverdb.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO implements Serializable {

    private Long id;

    private User user;

    private List<OrderItem> orderItems;

    private OrderStatus status;

    private Double price;

    private Double deliveryPrice;

    private DeliveryAddress deliveryAddress;

    private String phoneNumber;

    private LocalDateTime deliveryDate;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public OrderDTO() {
    }

    public OrderDTO(Long id, User user, List<OrderItem> orderItems,
                    OrderStatus status, Double price, Double deliveryPrice,
                    DeliveryAddress deliveryAddress, String phoneNumber,
                    LocalDateTime deliveryDate, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.user = user;
        this.orderItems = orderItems;
        this.status = status;
        this.price = price;
        this.deliveryPrice = deliveryPrice;
        this.deliveryAddress = deliveryAddress;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
