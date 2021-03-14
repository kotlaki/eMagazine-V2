package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {

    List<DeliveryAddress> getUserAddresses(Long userId);

    DeliveryAddress getUserAddressById(Long id);

    void save(DeliveryAddress deliveryAddress);

}
