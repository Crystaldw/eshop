package de.telran.eshop.controller;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.User;
import de.telran.eshop.repository.UserRepository;
import de.telran.eshop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

/**
 * Контроллер для управления запросами, связанными с пользователями.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Конструктор контроллера.
     * @param userService Сервис пользователей для взаимодействия с базой данных пользователей.
     */
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Метод для отображения списка всех пользователей.
     * @param model Модель, используемая для передачи данных на представление.
     * @return Имя представления для страницы списка пользователей.
     */
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userlist";
    }

    /**
     * Метод для создания нового пользователя.
     * Доступен только администратору.
     * @param model Модель, используемая для передачи данных на представление.
     * @return Имя представления для страницы создания нового пользователя.
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // доступ к данному методу только администратору
    @GetMapping("/new")
    public String newUser(Model model) {
        System.out.println("Вызван метод создания нового пользователя"); // для меня
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    /**
     * Метод для сохранения нового пользователя.
     * @param userDto DTO нового пользователя.
     * @param model Модель, используемая для передачи данных на представление.
     * @return Перенаправление на страницу списка пользователей в случае успешного сохранения, иначе возврат на страницу создания нового пользователя.
     */
    @PostMapping("/new")
    public String saveUser(UserDTO userDto, Model model) {
        if (userService.save(userDto)) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", userDto);
            return "user";
        }
    }

    /**
     * Метод для получения ролей пользователя.
     * @param username Имя пользователя.
     * @return Строка с ролями пользователя.
     */
    @PostAuthorize("isAuthenticated() and #username==authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username) {
        System.out.println("Вызван метод получения ролей пользователя");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }


    /**
     * Метод для отображения профиля текущего пользователя.
     * @param model Модель, используемая для передачи данных на представление.
     * @param principal Интерфейс для доступа к информации о текущем пользователе.
     * @return Имя представления для страницы профиля пользователя.
     */
    @PreAuthorize("isAuthenticated()") //метод может быть вызван только аутентифицированными пользователями
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("Вы не авторизованы");
        }
        User user = userService.findByName(principal.getName());

        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("user", dto);
        return "profile";
    }

    /**
     * Метод для обновления профиля текущего пользователя.
     * @param dto DTO с обновленной информацией о пользователе.
     * @param model Модель, используемая для передачи данных на представление.
     * @param principal Интерфейс для доступа к информации о текущем пользователе.
     * @return Перенаправление на страницу профиля пользователя.
     */
    @PreAuthorize("isAuthenticated()") //метод может быть вызван только аутентифицированными пользователями
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())) {
            throw new RuntimeException("Вы не авторизованы");
        }
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())) {
            model.addAttribute("user", dto);
            throw new RuntimeException("Пароли не совпадают");
            // добавить какое-то сообщение
        }
        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable User id){
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
