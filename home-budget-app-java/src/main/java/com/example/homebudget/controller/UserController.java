package com.example.homebudget.controller;

import com.example.homebudget.model.ApplicationUser;
import com.example.homebudget.repository.ApplicationUserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class UserController {
    private final ApplicationUserRepository repository;

    public UserController(ApplicationUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new ApplicationUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute ApplicationUser user) {
        repository.save(user);
        return "redirect:/logowanie";
    }

    @PostMapping("/account/password")
    public String changePassword(@RequestParam String password, Principal principal) {
        ApplicationUser u = repository.findByEmail(principal.getName());
        if (u != null) {
            u.setPassword(password);
            repository.save(u);
        }
        return "redirect:/account";
    }

    @PostMapping("/account/delete")
    public String deleteAccount(Principal principal) {
        ApplicationUser u = repository.findByEmail(principal.getName());
        if (u != null) {
            repository.delete(u);
        }
        return "redirect:/";
    }
}
