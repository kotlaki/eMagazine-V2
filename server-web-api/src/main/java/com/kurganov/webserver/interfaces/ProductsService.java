package com.kurganov.webserver.interfaces;

import com.kurganov.serverdb.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductsService {

    Product findByTitle(String title);

    Product findById(Long id);

    List<Product> getAllProducts();

    Page<Product> getProductsByCost(Pageable pageable, Double min, Double max);

    Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize,
                                                    Specification<Product> productSpecification);

    Product saveOrUpdate(Product product);

    void deleteById(Long id);

}
