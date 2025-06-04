package com.example.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/expenses")
    public String expenses() {
        return "forward:/expenses.html";
    }

    @GetMapping("/incomes")
    public String incomes() {
        return "forward:/incomes.html";
    }
}
