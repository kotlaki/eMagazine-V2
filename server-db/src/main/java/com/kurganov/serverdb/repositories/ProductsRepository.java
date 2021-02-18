package com.kurganov.serverdb.repositories;

import com.kurganov.serverdb.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByPriceBetween(Pageable pageable, double minPrice, double maxPrice);

    Product findOneByTitle(String title);

    void deleteById(Long id);
}
