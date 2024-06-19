package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests // Change to authorizeHttpRequests
                        .requestMatchers("/movie/newMovie", "/show/newShow").authenticated()
                        .requestMatchers("/movie/", "/user/register", "/user/addUser", "/movie/{id}", "/show/{id}").permitAll()
                        .anyRequest().authenticated()
                )
                //.formLogin(Customizer.withDefaults())
                .formLogin((form) -> form
                        .loginPage("/user/login") // Custom login page
                        .loginProcessingUrl("/user/loginFinish") // Form submission URL
                        .failureUrl("/user/login?error=true")
                        .defaultSuccessUrl("/movie/", true)
                        .permitAll()) // Permit all to access the login page
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/user/login")
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .exceptionHandling((exceptions) -> exceptions
                        .accessDeniedHandler(accessDeniedHandler()));

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }
}