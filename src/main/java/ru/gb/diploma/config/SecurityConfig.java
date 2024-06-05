package ru.gb.diploma.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.diploma.repositories.iAppBalanceRepository;
import ru.gb.diploma.repositories.iUserRepository;
import ru.gb.diploma.services.AppBalanceService;
import ru.gb.diploma.services.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final iUserRepository repository;
    private final AppBalanceService appBalanceService;

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService(repository, appBalanceService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Основной Фильтр
    @Bean
    SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("home", "/auth/register", "/test/**", "/product/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((login) -> login
                        .permitAll()
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/home", true)
                );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService());
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }
}
