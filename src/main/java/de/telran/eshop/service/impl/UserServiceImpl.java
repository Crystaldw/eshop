package de.telran.eshop.service.impl;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.enums.Role;
import de.telran.eshop.entity.User;
import de.telran.eshop.repository.UserRepository;
import de.telran.eshop.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
/**
 * Реализация сервиса пользователей.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Сохраняет нового пользователя.
     *
     * @param userDTO данные нового пользователя
     * @return true, если пользователь успешно сохранен, иначе false
     * @throws RuntimeException если пароль пользователя не совпадает с подтверждением
     */
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

    /**
     * Сохраняет пользователя в базе данных.
     *
     * @param user пользователь для сохранения
     */
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Override
    public List<UserDTO> getAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Преобразует сущность пользователя в объект DTO.
     *
     * @param user пользователь для преобразования
     * @return DTO объект пользователя
     */
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    /**
     * Находит пользователя по имени.
     *
     * @param name имя пользователя
     * @return найденный пользователь или null, если пользователь не найден
     */
    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);  //найти по имени
    }

    /**
     * Обновляет профиль пользователя.
     *
     * @param dto данные пользователя для обновления
     * @throws RuntimeException если пользователь не найден по имени
     */
    @Override
    @Transactional
    public void updateProfile(UserDTO dto) {
        User saveUser = userRepository.findFirstByName((dto.getUsername()));  // Находим пользователя по имени из объекта DTO
        if (saveUser == null) {
            throw new RuntimeException("User not found by name " + dto.getUsername()); //если пользоаатель не найден выкидываем исключение
        }
        boolean isChanged = false;
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) { // Проверяем, не является ли пароль пустым
            saveUser.setPassword(passwordEncoder.encode(dto.getPassword())); // Если пароль не пустой, хешируем его и устанавливаем для пользователя
            isChanged = true;
        }
        if (!Objects.equals(dto.getEmail(), saveUser.getEmail())) { // Проверяем, отличается ли email пользователя в объекте DTO от текущего email
            saveUser.setEmail(dto.getEmail()); // Если email отличается, устанавливаем новый email для пользователя
            isChanged = true;
        }

        if (isChanged) { // Если были внесены изменения в профиль пользователя, сохраняем пользователя
            userRepository.save(saveUser);  // если небыло изменений мы не будем ничего сохранять
        }
    }
}