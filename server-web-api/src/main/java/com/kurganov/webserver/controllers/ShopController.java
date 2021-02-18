package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.Greeting;
import com.kurganov.serverdb.entities.Product;
import com.kurganov.serverdb.entities.ProductImage;
import com.kurganov.webserver.config.FilterApp;
import com.kurganov.webserver.interfaces.IShopControllerWs;
import com.kurganov.webserver.services.CategoryServiceImpl;
import com.kurganov.webserver.services.ImageServiceImpl;
import com.kurganov.webserver.services.ProductsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 5;

    private ProductsServiceImpl productsServiceImpl;
    private CategoryServiceImpl categoryServiceImpl;
    private ImageServiceImpl imageServiceImpl;

    private IShopControllerWs shopControllerWs;

    @Autowired
    public void setShopControllerWs(IShopControllerWs shopControllerWs) {
        this.shopControllerWs = shopControllerWs;
    }

    @Autowired
    public void setCategoryService(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @Autowired
    public void setImageServiceImpl(ImageServiceImpl imageServiceImpl) {
        this.imageServiceImpl = imageServiceImpl;
    }

    @Autowired
    public void setProductsService(ProductsServiceImpl productsServiceImpl) {
        this.productsServiceImpl = productsServiceImpl;
    }

    private FilterApp filterApp;

    @Autowired
    public void setFilterApp(FilterApp filterApp) {
        this.filterApp = filterApp;
    }

    @GetMapping
    public String shopPage(Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) Optional<String> word,
                           @RequestParam(value = "min", required = false) Optional<Double> min,
                           @RequestParam(value = "max", required = false) Optional<Double> max
    ) {

        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Product> products = filterApp.filterApp(word, min, max, currentPage, PAGE_SIZE);

        model.addAttribute("products", products.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("filters", "");
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        word.ifPresent(s -> model.addAttribute("word", s));
        return "shop-page";
    }

    @GetMapping("/product_info/{id}")
    public String productPage(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsServiceImpl.findById(id);
        model.addAttribute("product", product);
        return "product-page";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/add/{id}")
    public String addProductPage(Model model, @PathVariable("id") Long id) {
        Product product = productsServiceImpl.findById(id);
        if (product == null) {
            product = new Product();
        }
        model.addAttribute("categories", categoryServiceImpl.getAllCategories());
        model.addAttribute("product", product);
        return "edit-product";
    }

    // Binding Result после @ValidModel !!!
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model, HttpServletRequest httpServletRequest) throws InterruptedException {
        if (bindingResult.hasErrors()) {
            return "edit-product";
        }

        Product existing = productsServiceImpl.findByTitle(product.getTitle());
        if (existing != null && (product.getId() == null || !product.getId().equals(existing.getId()))) {
            model.addAttribute("product", product);
            model.addAttribute("productCreationError", "Product title already exists");
            return "edit-product";
        }
        productsServiceImpl.saveOrUpdate(product);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shopControllerWs.sendMessage("/topic/greetings", new Greeting(""));
            }
        }).start();
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        productsServiceImpl.deleteById(id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/edit/{id}")
    public String editById(@PathVariable("id") Long id, Model model) {
        Product product = productsServiceImpl.findById(id);
        if (product == null) {
            product = new Product();
//            product.setId(0L);
        }

        model.addAttribute("categories", categoryServiceImpl.getAllCategories());
        model.addAttribute("product", product);
        return "edit-product";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("product") Product product, Model model,
                         BindingResult bindingResult, HttpServletRequest httpServletRequest,
                         @RequestParam("file") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryServiceImpl.getAllCategories());
            return "edit-product";
        }
//        Product product = productsService.findById(id);
        if(!file.isEmpty()) {
            String path = imageServiceImpl.saveFile(file);
            ProductImage productImage = new ProductImage();
            productImage.setPath(path);
            productImage.setProduct(product);
            product.addImage(productImage);
        }
        productsServiceImpl.saveOrUpdate(product);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shopControllerWs.sendMessage("/topic/greetings", new Greeting(""));
            }
        }).start();
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

}
