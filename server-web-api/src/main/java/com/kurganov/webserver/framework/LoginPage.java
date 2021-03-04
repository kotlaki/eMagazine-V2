package com.kurganov.webserver.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"username\"]")
//    @FindBy(css = "#username")
//    @FindBy(id = "username")
    private WebElement loginField;

    @FindBy(xpath = "//*[@id=\"password\"]")
//    @FindBy(css = "#password")
//    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(xpath = "/html/body/div/form/button")
//    @FindBy(css = ".btn")
    private WebElement button;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
    }

    public void inputLogin(String login) {
//        new WebDriverWait(webDriver, 20)
//                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"username\"]")));
        loginField.sendKeys(login);
    }

    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
//        new WebDriverWait(webDriver, 5)
//                .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/form/button")));
        button.click();
    }

}
