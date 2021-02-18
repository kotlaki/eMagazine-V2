package com.kurganov.webserver.controllers;

import com.kurganov.serverdb.entities.Product;
import com.kurganov.webserver.services.ProductsServiceImpl;
import com.kurganov.webserver.utils.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/shop/cart")
public class ShoppingCartController {
    private ShoppingCart shoppingCart;
    private ProductsServiceImpl productsService;

    @Autowired
    public void setShoppingCartService(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Autowired
    public void setProductsService(ProductsServiceImpl productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String cartPage(Model model) {
        model.addAttribute("cart", shoppingCart);
        return "cart-page";
    }

    @GetMapping("/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCart.add(id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        Product product = productsService.findById(id);
        shoppingCart.remove(product);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }
}
