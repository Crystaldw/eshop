package de.telran.eshop.config;

import de.telran.eshop.entity.Role;
import de.telran.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeRequest) ->
                        authorizeRequest
                                .requestMatchers("/users/new").hasAuthority(Role.ADMIN.name())
                                .anyRequest().permitAll()
                )
                .formLogin(login->login
                        .loginPage("/login")
                        .failureUrl("/login-error")
                        .loginProcessingUrl("/auth")
                        .permitAll()
                )
                .logout(customizer -> customizer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );
        return http.build();

    }
}