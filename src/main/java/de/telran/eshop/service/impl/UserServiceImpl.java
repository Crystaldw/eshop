package de.telran.eshop.service.impl;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.enums.Role;
import de.telran.eshop.entity.User;
import de.telran.eshop.repository.UserRepository;
import de.telran.eshop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            throw new RuntimeException("Password is not equals");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);  //найти по имени
    }

    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User saveUser = userRepository.findFirstByName((dto.getUsername()));  // находим пользователя по dto
        if (saveUser == null) {
            throw new RuntimeException("User not found by name " + dto.getUsername()); //если пользоаатель не найден выкидываем ошибку
        }


        boolean isChanged = false;
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            saveUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            isChanged = true;
        }
        if (!Objects.equals(dto.getEmail(), saveUser.getEmail())) {
            saveUser.setEmail(dto.getEmail());
            isChanged = true;
        }

        if (isChanged) {
            userRepository.save(saveUser);  // если небыло изменений мы не будем ничего сохранять
        }
    }
}