package com.example.homebudget.controller;

import com.example.homebudget.model.Expense;
import com.example.homebudget.service.ExpenseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String edit(@PathVariable Long id, Model model) {
        Expense expense = expenseService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("expense", expense);
        return "expense";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Expense updated) {
        Expense expense = expenseService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        expense.setCategory(updated.getCategory());
        expense.setDate(updated.getDate());
        expense.setAmount(updated.getAmount());
        expense.setNotes(updated.getNotes());
        expenseService.save(expense);
        return "redirect:/expenses";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        expenseService.delete(id);
        return "redirect:/expenses";
    }
}
