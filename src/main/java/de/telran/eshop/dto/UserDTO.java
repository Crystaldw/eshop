package de.telran.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
DTO расшифровывается как "Data Transfer Object" (Объект передачи
данных). Это шаблон проектирования, используемый для передачи
данных между компонентами системы. DTO представляет собой объект,
который переносит данные между уровнями приложения, такими как у
ровень представления (например, пользовательский интерфейс),
уровень обработки данных и уровень доступа к данным.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String username;

    private String password;

    private String matchingPassword;

    private String email;

}
