//package com.sandee007.appointmentScheduleSystem.security.configs;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(config ->
//                                               config
//                                                       .requestMatchers("/").permitAll()
//                                                       .anyRequest().authenticated()
//                );
//
//        return httpSecurity.build();
//    }
//
//}
