package com.sandee007.appointmentScheduleSystem.base.libraries;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumWebDriverLibrary {

    @Bean
    public WebDriver getChromeDriver(){
        WebDriverManager.chromiumdriver().setup();
        return new ChromeDriver();
    }
}
