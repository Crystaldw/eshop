package de.telran.eshop.service;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService { //security

    boolean save(UserDTO userDTO);
    List<UserDTO> getAll();

    User findByName(String name);
    void updateProfile(UserDTO userDTO);
}