package com.kurganov.webserver.config;


import com.kurganov.serverdb.entities.Product;
import com.kurganov.serverdb.entities.User;
import com.kurganov.serverdb.repositories.specifications.ProductSpecs;
import com.kurganov.serverdb.repositories.specifications.UserSpecs;
import com.kurganov.webserver.interfaces.UserService;
import com.kurganov.webserver.services.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FilterApp {

    private UserService userService;
    private ProductsServiceImpl productsServiceImpl;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProductsService(ProductsServiceImpl productsServiceImpl) {
        this.productsServiceImpl = productsServiceImpl;
    }

    public Page<Product> filterApp(Optional<String> word, Optional<Double> min,
                                   Optional<Double> max, int currentPage, int PAGE_SIZE) {

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word.isPresent()) {
            spec = spec.and(ProductSpecs.titleContains(word.get()));
            filters.append("&word=" + word);
        }
        if (min.isPresent()) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min.get()));
            filters.append("&min=" + min);
        }
        if (max.isPresent()) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max.get()));
            filters.append("&max=" + max);
        }

        Page<Product> products = productsServiceImpl.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, spec);
        return products;
    }

    public Page<User> filterAppUsers(Optional<String> word, int currentPage, int PAGE_SIZE) {

        Specification<User> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word.isPresent()) {
            spec = spec.and(UserSpecs.titleContains(word.get()));
            filters.append("&word=" + word);
        }

        Page<User> users = userService.getUsersWithPagingAndFiltering(currentPage, PAGE_SIZE, spec);
        return users;
    }
}
