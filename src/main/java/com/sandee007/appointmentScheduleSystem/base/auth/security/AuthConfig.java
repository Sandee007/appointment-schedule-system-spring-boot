package com.sandee007.appointmentScheduleSystem.base.auth.security;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class AuthConfig {

    //    @Autowired
    //    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //        can only use the username column here, nothing else
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, enabled FROM users " +
                        " WHERE username=? "
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT R.user_id, R.role FROM roles AS R " +
                        " LEFT JOIN users AS U " +
                        " ON U.id = R.user_id " +
                        " WHERE U.username=?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(config ->
                                               config
                                                       .requestMatchers(
                                                               "/",
                                                               "/assets/**",
                                                               "/register/**",
                                                               "anotherUrl"
                                                       ).permitAll()
                                                       .requestMatchers("/leaders/**").hasRole(Role.MANAGER.name())
                                                       .requestMatchers("/systems/**").hasRole(Role.ADMIN.name())
                                                       .anyRequest().authenticated()
                )
                .formLogin(loginForm ->
                                   loginForm
                                           //                                           .loginPage("/login")
                                           // * spring will handle a POST request for this automatically, must have name,password
                                           //                                           .loginProcessingUrl("/handle-login")
                                           .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(config ->
                                           config
                                                   .accessDeniedPage("/access-denied")
                );

        return httpSecurity.build();
    }

    //    @Bean
    //    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    //        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    //        auth.setUserDetailsService(userDetailsService);
    //        auth.setPasswordEncoder(passwordEncoder());
    //        return auth;
    //    }
}
