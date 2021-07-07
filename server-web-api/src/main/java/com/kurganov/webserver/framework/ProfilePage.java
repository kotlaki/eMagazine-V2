package com.kurganov.webserver.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilePage {

    public WebDriver webDriver;

    public ProfilePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "/html/body/div[1]/nav/div/ul/li[6]/form/input[2]")
    private WebElement logOutBtn;

    public void userLogout() {
        logOutBtn.click();
    }

}
