package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO, представляющий информацию о пользователе.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;

    }

}
