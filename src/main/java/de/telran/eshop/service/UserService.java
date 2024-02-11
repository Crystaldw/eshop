package de.telran.eshop.service;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.User;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 */
public interface UserService {

    /**
     * Сохраняет нового пользователя.
     *
     * @param userDTO информация о пользователе в формате DTO
     * @return true, если пользователь успешно сохранен, в противном случае - false
     */
    boolean save(UserDTO userDTO);

    /**
     * Сохраняет пользователя.
     *
     * @param user пользователь для сохранения
     */
    void save(User user);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список всех пользователей в формате DTO
     */
    List<UserDTO> getAll();

    /**
     * Находит пользователя по его имени.
     *
     * @param name имя пользователя
     * @return найденный пользователь или null, если пользователь не найден
     */
    User findByName(String name);

    /**
     * Обновляет профиль пользователя.
     *
     * @param userDTO новая информация о пользователе в формате DTO
     */
    void updateProfile(UserDTO userDTO);
}
