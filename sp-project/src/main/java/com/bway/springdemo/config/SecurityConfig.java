package com.bway.springdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	return http.csrf(csrf -> csrf.disable())
    		    .authorizeHttpRequests(auth -> auth
    		        .requestMatchers("/user/**").hasRole("USER")
    		        .requestMatchers("/admin/**").hasRole("ADMIN")
    		        .requestMatchers("/users/login", "/users/signup", "/css/**", "/js/**","/forgotPassword","/send-recovery-code","/verify-code","/reset-password").permitAll()
    		        .anyRequest().authenticated()
    		    )
                .formLogin(form -> form
                        .loginPage("/users/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler((request, response, exception) -> {
                            System.out.println("Login failed: " + exception.getMessage());
                            response.sendRedirect("/users/login?error");
                        })
                        .permitAll())
                .authenticationProvider(authenticationProvider())
                .logout(logout -> logout
                		  .logoutRequestMatcher(new AntPathRequestMatcher("/users/logout")) // Define logout URL
                          .invalidateHttpSession(true) // Invalidate session
                          .clearAuthentication(true) // Clear authentication details
                          .deleteCookies("JSESSIONID") // Delete cookies
                          .logoutSuccessUrl("/users/login") // Redirect to login page after logout
                          .logoutSuccessHandler((request, response, authentication) -> {
                              response.setStatus(200); // Optional: Custom response on successful logout
                              response.sendRedirect("/users/login"); // Redirect to login page
                          }))
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
