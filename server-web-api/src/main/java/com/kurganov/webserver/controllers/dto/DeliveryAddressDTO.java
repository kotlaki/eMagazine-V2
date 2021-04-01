package com.kurganov.webserver.controllers.dto;

import com.kurganov.serverdb.entities.User;

import java.io.Serializable;

public class DeliveryAddressDTO implements Serializable {

    private Long id;

    private User user;

    private String address;

    public DeliveryAddressDTO() {
    }

    public DeliveryAddressDTO(Long id, User user, String address) {
        this.id = id;
        this.user = user;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
