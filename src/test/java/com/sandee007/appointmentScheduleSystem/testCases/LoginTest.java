package com.sandee007.appointmentScheduleSystem.testCases;

import com.sandee007.appointmentScheduleSystem.pages.HomePage;
import com.sandee007.appointmentScheduleSystem.pages.LoginPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginTest {

    @Autowired
    private HomePage homePage;

    @Autowired
    private LoginPage loginPage;

    private final String EMPTY_STRING = "";
    private final String WRONG_USERNAME = "wrongUsername";
    private final String WRONG_PASSWORD = "wrongPassword";
    private final String CORRECT_USERNAME = "admin@gmail.com";
    private final String CORRECT_PASSWORD = "12345678";

    private final String[][] LOGIN_TEST_DATA = {
            {EMPTY_STRING, EMPTY_STRING},
            {WRONG_USERNAME, EMPTY_STRING},
            {EMPTY_STRING, WRONG_PASSWORD},
            {WRONG_USERNAME, WRONG_PASSWORD},
            {CORRECT_USERNAME, EMPTY_STRING},
            {EMPTY_STRING, CORRECT_PASSWORD},
            {CORRECT_USERNAME, CORRECT_PASSWORD},
    };

    public void performLogin() {
        homePage.visitLoginPage();

        for (String[] i : LOGIN_TEST_DATA) {
            loginPage.clickLoginBtn(i[0], i[1]);
        }

        System.out.println("Test Complete: " + this.getClass());
    }
}
