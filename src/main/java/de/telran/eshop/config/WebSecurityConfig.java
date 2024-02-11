package de.telran.eshop.config;

import de.telran.eshop.entity.User;
import de.telran.eshop.entity.enums.Role;
import de.telran.eshop.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для настройки безопасности веб-приложения с использованием Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

    /**
     * Определяет сервис пользователей для Spring Security, используя репозиторий пользователей.
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        UserDetailsService userDetailsService = (username) -> {
            User user = userRepository.findFirstByName(username);
            if (user != null) {
                return user;
            } else {
                throw new UsernameNotFoundException("ERROR-NOT USER");
            }
        };
        return userDetailsService;
    }

    /**
     * Определяет кодировщик паролей для использования в Spring Security.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    /**
     * Определяет цепочку фильтров безопасности для HTTP запросов.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequest) ->
                        authorizeRequest
                                .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name())
                                .requestMatchers("/users").hasAnyAuthority(Role.ADMIN.name(), Role.MANAGER.name())
                                .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .failureUrl("/login-error")
                        .loginProcessingUrl("/auth")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );
        return http.build();
    }

}
