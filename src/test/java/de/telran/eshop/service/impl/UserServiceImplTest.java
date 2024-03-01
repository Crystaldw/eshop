package de.telran.eshop.service.impl;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.User;
import de.telran.eshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepository userRepository;
    @Mock
    UserServiceImpl userService;

    @BeforeEach
    void setUp(){
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void checkSave(){
        //have
        // Создаем объект UserDto, представляющий данные пользователя
        UserDTO userDto = UserDTO.builder()
                .email("email")
                .password("password")
                .matchingPassword("password")
                .build();

        // Мокируем поведение кодировщика пароля, чтобы возвращать "password" для любого ввода
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("password");

        //execute
        boolean result = userService.save(userDto); // Вызываем метод save() userService с объектом userDto

        //check
        Assertions.assertTrue(result); // Проверяем, что результат операции сохранения равен true, что указывает на успех
        Mockito.verify(passwordEncoder).encode(Mockito.anyString()); // Проверяем, что метод encode() кодировщика пароля был вызван хотя бы один раз с любой строковым аргументом
        Mockito.verify(userRepository).save(Mockito.any(User.class)); // Проверяем, что метод save() userRepository был вызван с аргументом типа User

    }


    @Test
    void getAll() {
    }

    @Test
    void findByNameTest() {
    }

    @Test
    void updateProfile() {
    }
}