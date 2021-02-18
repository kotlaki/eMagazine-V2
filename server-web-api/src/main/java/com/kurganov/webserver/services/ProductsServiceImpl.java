package com.kurganov.webserver.services;

import com.kurganov.serverdb.entities.Product;
import com.kurganov.serverdb.repositories.ProductsRepository;
import com.kurganov.webserver.interfaces.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    private ProductsRepository productsRepository;

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public Product findByTitle(String title) {
        return productsRepository.findOneByTitle(title);
    }

    public Product findById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return (List<Product>) productsRepository.findAll();
    }

    public Page<Product> getProductsByCost(Pageable pageable, Double min, Double max) {
        if (min == null) {
            min = 0.0;
        }
        if (max == null) {
            max = Double.MAX_VALUE;
        }
        return productsRepository.findAllByPriceBetween(pageable, min, max);
    }

    public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize,
                                                           Specification<Product> productSpecification) {
        return productsRepository.findAll(productSpecification, PageRequest.of(pageNumber, pageSize));
    }

    public Product saveOrUpdate(Product product) {
        return productsRepository.save(product);
    }

//    @Transactional
//    public void update(Product product) {
//        Product productNew = new Product();
//        productNew.setId(product.getId());
//        productNew.setCategory(product.getCategory());
//        productNew.setVendorCode(product.getVendorCode());
//        productNew.setTitle(product.getTitle());
//        productNew.setShortDescription(product.getShortDescription());
//        productNew.setFullDescription(product.getFullDescription());
//        productNew.setPrice(product.getPrice());
//        productNew.setCreateAt(product.getCreateAt());
//        productNew.setUpdateAt(LocalDateTime.now());
//        productsRepository.save(product);
//    }

    public void deleteById(Long id) {
        productsRepository.deleteById(id);
    }
}
