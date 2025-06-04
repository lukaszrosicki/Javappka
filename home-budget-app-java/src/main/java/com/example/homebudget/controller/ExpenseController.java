package com.example.homebudget.controller;

import com.example.homebudget.model.Expense;
import com.example.homebudget.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        model.addAttribute("expense", new Expense());
        return "expenses";
    }

    @PostMapping
    public String create(@ModelAttribute Expense expense) {
        expenseService.save(expense);
        return "redirect:/expenses";
    }

    @GetMapping("/summary")
    @ResponseBody
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return expenseService.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Expense> get(@PathVariable Long id) {
        return expenseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        expenseService.delete(id);
    }
}
