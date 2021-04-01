package com.kurganov.webserver;

import org.testng.annotations.Test;

public class LoginAndProductsListTestSelenium extends BaseTestSelenium {

    @Test
    public void loginTest() {
        webDriver.get("http://localhost:8188/login");
        loginPage.inputLogin("test");
        loginPage.inputPassword("123");
        loginPage.clickLoginButton();
        shopPage.goShop();
        shopPage.listProduct();
        profilePage.userLogout();
    }

}
