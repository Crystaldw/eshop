package de.telran.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EshopApplication {

    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(EshopApplication.class, args);
//        PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
//        System.out.println(encoder.encode("pass"));
       SpringApplication.run(EshopApplication.class, args);
    }
}
