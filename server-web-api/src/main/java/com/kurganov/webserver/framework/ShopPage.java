package com.kurganov.webserver.framework;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ShopPage {

    public WebDriver webDriver;

    public ShopPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "/html/body/div[1]/nav/div/ul/li[2]/a")
    private WebElement btnShop;

    public void listProduct() {
        // находим список продуктов на странице
        List<WebElement> webElement = webDriver.findElements(By.cssSelector(".table-product"));
        System.out.println("quantity products = " + webElement.size());
        // перебераем список элементов и выводим названия товаров в консоль
        for (WebElement o: webElement) {
            String txtElement = o.findElement(By.cssSelector(".name-product")).getText();
            System.out.println(txtElement);
        }
    }

    public void goShop() {
        btnShop.click();
    }

}
