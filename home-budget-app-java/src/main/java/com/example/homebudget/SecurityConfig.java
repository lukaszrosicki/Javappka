package com.example.homebudget;

import com.example.homebudget.model.ApplicationUser;
import com.example.homebudget.repository.ApplicationUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/register", "/logowanie", "/style.css").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/logowanie")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout.logoutSuccessUrl("/"))
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(ApplicationUserRepository repo) {
        return username -> {
            ApplicationUser u = repo.findByEmail(username);
            if (u == null) {
                throw new UsernameNotFoundException(username);
            }
            return User.withUsername(u.getEmail())
                    .password("{noop}" + u.getPassword())
                    .roles("USER")
                    .build();
        };
    }
}
