package de.telran.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping({"/", ""})
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/login-error") //чтобы пользователь попал на login page
    public String loginError(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }
}
