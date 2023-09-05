package com.sandee007.appointmentScheduleSystem.pages;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LoginPage {

    @Autowired
    private WebDriver webDriver;

    @PostConstruct
    public void postConstruct() {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(name = "username")
    private WebElement inputUsername;

    @FindBy(name = "password")
    private WebElement inputPassword;

    @FindBy(id = "btn_login")
    private WebElement btnLogin;

    public void clickLoginBtn(String username, String password){
        inputUsername.sendKeys(username);
        inputPassword.sendKeys(password);
        System.out.println("Credentials : " + username + " " + password);
        btnLogin.click();
    }
}
