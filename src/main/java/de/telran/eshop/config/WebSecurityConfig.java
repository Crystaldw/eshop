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


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequest) ->
                                authorizeRequest
                                .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name()) //заменил анотацией в контроллере @PreAuthorize
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