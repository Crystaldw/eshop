package de.telran.eshop.service;

import de.telran.eshop.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService { //security

    boolean save(UserDTO userDTO);
}