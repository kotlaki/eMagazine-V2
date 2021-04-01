package com.kurganov.serverdb.repositories;

import com.kurganov.serverdb.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getOrdersByUserId(Long userId);
    void deleteById(Long id);
}
