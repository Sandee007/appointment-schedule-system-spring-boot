package com.sandee007.appointmentScheduleSystem;

import com.sandee007.appointmentScheduleSystem.pages.AdminDashboardPage;
import com.sandee007.appointmentScheduleSystem.pages.HomePage;
import com.sandee007.appointmentScheduleSystem.pages.LoginPage;
import com.sandee007.appointmentScheduleSystem.testCases.LoginTest;
import com.sandee007.appointmentScheduleSystem.testCases.RegisterConsultantTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AppointmentScheduleSystemApplicationTests {

	@Autowired
	private LoginTest loginTest;

	@Autowired
	private RegisterConsultantTest registerConsultantTest;


	@Value("${testing.app.url}")
	private String appUrl;

	@Autowired
	private WebDriver webDriver;

	@Value("${testing.browsers}")
	private List<String> browsers;

	@Test
	void loginTest() {
		webDriver.navigate().to(appUrl);
		loginTest.performLogin();
//		* register consultant test
//		registerConsultantTest.performRegisterConsultant();
	}


}
