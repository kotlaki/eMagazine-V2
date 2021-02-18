package com.kurganov.webserver.controllers.dto;

import com.kurganov.serverdb.entities.Category;
import com.kurganov.serverdb.entities.ProductImage;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO implements Serializable {

    private Long id;

    @NotEmpty
    private Category category;

    private String vendorCode;

    private List<ProductImage> images;

    private String title;

    private String shortDescription;

    private String fullDescription;

    @NotEmpty
    private double price;

    @NotEmpty
    private LocalDateTime createAt;

    @NotEmpty
    private LocalDateTime updateAt;


}
