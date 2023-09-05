package com.sandee007.appointmentScheduleSystem.testCases;

import com.sandee007.appointmentScheduleSystem.pages.AdminDashboardPage;
import com.sandee007.appointmentScheduleSystem.pages.RegisterConsultantPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterConsultantTest {

    @Autowired
    private AdminDashboardPage adminDashboardPage;

    @Autowired
    private RegisterConsultantPage registerConsultantPage;

    public void performRegisterConsultant(){
        adminDashboardPage.visitRegisterConsultantPage();
        registerConsultantPage.performRegisterConsultant();
        System.out.println("Test Complete: " + this.getClass());
    }

}
