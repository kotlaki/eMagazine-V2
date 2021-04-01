package com.kurganov.webserver;

import com.kurganov.webserver.framework.LoginPage;
import com.kurganov.webserver.framework.ProfilePage;
import com.kurganov.webserver.framework.ShopPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTestSelenium {

    WebDriver webDriver;
    LoginPage loginPage;
    ProfilePage profilePage;
    ShopPage shopPage;

    @BeforeClass
    public void init() {
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().window().maximize();
        this.loginPage = new LoginPage(webDriver);
        this.profilePage = new ProfilePage(webDriver);
        this.shopPage = new ShopPage(webDriver);
    }

    @AfterClass
    public void shutdown() {
        this.webDriver.quit();
    }

}
