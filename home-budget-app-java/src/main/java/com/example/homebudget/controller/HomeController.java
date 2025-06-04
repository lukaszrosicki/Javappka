package com.example.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    public HomeController() {
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/logowanie")
    public String login() {
        return "login";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

}
