package com.example.homebudget.controller;

import com.example.homebudget.model.Income;
import com.example.homebudget.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("incomes", incomeService.findAll());
        model.addAttribute("income", new Income());
        return "incomes";
    }

    @PostMapping
    public String create(@ModelAttribute Income income) {
        incomeService.save(income);
        return "redirect:/incomes";
    }

    @GetMapping("/summary")
    @ResponseBody
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return incomeService.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Income income = incomeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("income", income);
        return "income";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Income updated) {
        Income income = incomeService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        income.setCategory(updated.getCategory());
        income.setDate(updated.getDate());
        income.setAmount(updated.getAmount());
        income.setNotes(updated.getNotes());
        incomeService.save(income);
        return "redirect:/incomes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        incomeService.delete(id);
        return "redirect:/incomes";
    }
}
