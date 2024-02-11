package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO, представляющий информацию о пользователе.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Подтверждение пароля пользователя.
     */
    private String matchingPassword;

    /**
     * Электронная почта пользователя.
     */
    private String email;
}
