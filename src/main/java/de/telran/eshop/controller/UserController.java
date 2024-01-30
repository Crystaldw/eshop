package de.telran.eshop.controller;

import de.telran.eshop.dto.UserDTO;
import de.telran.eshop.entity.User;
import de.telran.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Отображает список всех пользователей.
     */
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userlist";
    }

    /**
     * Позволяет администратору создавать нового пользователя.
     */
    @PreAuthorize("hasAnyAuthority('ADMIN')") // доступ к данному методу только администратору
    @GetMapping("/new")
    public String newUser(Model model) {
        System.out.println("called method newUser"); // для нас
        model.addAttribute("user", new UserDTO());
        return "user";
    }

    /**
     * Получает роли пользователя.
     */
    @PostAuthorize("isAuthenticated() and #username==authentication.principal.username")
    @GetMapping("/{name}/roles")
    @ResponseBody
    public String getRoles(@PathVariable("name") String username) {
        System.out.println("called method getRoles");
        User byName = userService.findByName(username);
        return byName.getRole().name();
    }


    /**
     * Сохраняет нового пользователя.
     */
    @PostMapping("/new")
    public String saveUser(UserDTO dto, Model model) {
        if (userService.save(dto)) {
            return "redirect:/users";
        } else {
            model.addAttribute("user", dto);
            return "user";
        }
    }

    /**
     * Отображает профиль пользователя.
     */
    @GetMapping("/profile")
    public String profileUser(Model model, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("You are not authorized");
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
     * Обновляет профиль пользователя.
     */
    @PostMapping("/profile")
    public String updateProfileUser(UserDTO dto, Model model, Principal principal) {
        if (principal == null || !Objects.equals(principal.getName(), dto.getUsername())) {
            throw new RuntimeException("You are not authorized");
        }
        if (dto.getPassword() != null
                && !dto.getPassword().isEmpty()
                && !Objects.equals(dto.getPassword(), dto.getMatchingPassword())) {
            model.addAttribute("user", dto);
            // добавить какое-то сообщение
            return "profile";
        }
        userService.updateProfile(dto);
        return "redirect:/users/profile";
    }
}
