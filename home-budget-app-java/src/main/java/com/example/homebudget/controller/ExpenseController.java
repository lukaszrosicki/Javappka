package com.example.homebudget.controller;

import com.example.homebudget.model.Expense;
import com.example.homebudget.repository.ExpenseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("expenses", expenseRepository.findAll());
        model.addAttribute("expense", new Expense());
        return "expenses";
    }

    @PostMapping
    public String create(@ModelAttribute Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/summary")
    @ResponseBody
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return expenseRepository.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Expense> get(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        expenseRepository.deleteById(id);
    }
}
