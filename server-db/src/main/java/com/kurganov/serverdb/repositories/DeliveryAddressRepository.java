package com.kurganov.serverdb.repositories;

import com.kurganov.serverdb.entities.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findAllByUserId(Long userId);
}
