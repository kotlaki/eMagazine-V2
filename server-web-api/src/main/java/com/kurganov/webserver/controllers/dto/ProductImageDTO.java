package com.kurganov.webserver.controllers.dto;

import com.kurganov.serverdb.entities.Product;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class ProductImageDTO implements Serializable {

    private Long id;

    @NotEmpty
    private Product product;

    @NotEmpty
    private String path;

    public ProductImageDTO() {
    }

    public ProductImageDTO(Long id, @NotEmpty Product product, @NotEmpty String path) {
        this.id = id;
        this.product = product;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
