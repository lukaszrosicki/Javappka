package com.example.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.homebudget.repository.ExpenseRepository;
import com.example.homebudget.repository.IncomeRepository;

@Controller
public class HomeController {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    public HomeController(ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
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

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required=false) Integer year,
                            @RequestParam(required=false) Integer month,
                            Model model) {
        if (year != null && month != null) {
            BigDecimal inc = incomeRepository.sumByMonthAndYear(year, month);
            BigDecimal exp = expenseRepository.sumByMonthAndYear(year, month);
            model.addAttribute("incomeSum", inc);
            model.addAttribute("expenseSum", exp);
            model.addAttribute("year", year);
            model.addAttribute("month", month);
        }
        return "dashboard";
    }
}
