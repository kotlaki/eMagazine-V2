package com.kurganov.serverdb.repositories.specifications;

import com.kurganov.serverdb.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {
    public static Specification<User> titleContains(String word) { // title LIKE 'apple%'
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("userName"), "%" + word + "%");
    }
}
