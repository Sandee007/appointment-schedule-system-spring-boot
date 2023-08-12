package com.sandee007.appointmentScheduleSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AppointmentScheduleSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentScheduleSystemApplication.class, args);
	}

}
