package de.telran.eshop.controller;

import de.telran.eshop.service.SessionObjectHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    /**
     * Отображает главную страницу приложения.
     * Добавляет количество кликов и UUID сеанса в модель, чтобы отобразить на странице.
     */
    @GetMapping({"/", ""})
    public String index(Model model, HttpSession httpSession){
        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
        if(httpSession.getAttribute("myID") ==null){   //User URL ID
            String uuid = UUID.randomUUID().toString();
            httpSession.setAttribute("myID", uuid);
            System.out.println("Generated UUID ->" + uuid);
        }
        model.addAttribute("uuid", httpSession.getAttribute("myID"));
        return "index";
    }

    /**
     * Отображает страницу входа в систему.
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * Обрабатывает ошибку входа в систему и отображает страницу входа в систему с сообщением об ошибке.
     */
    @RequestMapping("/login-error") //чтобы пользователь попал на login page
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }

    /**
     * Обрабатывает запрос на выход из системы пользователя.
     * После выхода из системы перенаправляет пользователя на главную страницу.
     */

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "redirect:/"; // или любая другая страница
    }
}
