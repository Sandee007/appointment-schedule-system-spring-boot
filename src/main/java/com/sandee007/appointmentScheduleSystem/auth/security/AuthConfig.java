package com.sandee007.appointmentScheduleSystem.auth.security;

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

    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder()
                               .username("john")
                               .password("{noop}123") // noop => no operation on password / not encrypted
                               .roles(Role.EMPLOYEE.name())
                               .build();

        UserDetails mary = User.builder()
                               .username("mary")
                               .password("{noop}123")
                               .roles(Role.EMPLOYEE.name(), Role.MANAGER.name())
                               .build();

        UserDetails susan = User.builder()
                                .username("susan")
                                .password("{noop}123")
                                .roles(Role.EMPLOYEE.name(), Role.MANAGER.name(), Role.ADMIN.name())
                                .build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }
    */

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "SELECT username, password, active FROM custom_table_users " +
                        " WHERE username=?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT R.user_id, R.role FROM custom_table_roles AS R " +
                        " LEFT JOIN custom_table_users AS U " +
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
                                                               "/register/**",
                                                               "/css/**",
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
                                           .loginProcessingUrl("/handle-login")
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
