package com.sandee007.appointmentScheduleSystem.pages;

import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterConsultantPage {
    @Autowired
    private WebDriver webDriver;

    @PostConstruct
    private void postConstruct() {
        PageFactory.initElements(webDriver, this);
    }

    @FindBy(id = "btn_submit")
    private WebElement btnSubmit;

    public void performRegisterConsultant(){
        btnSubmit.click();
    }
}
