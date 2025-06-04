package com.example.homebudget.controller;

import com.example.homebudget.model.Income;
import com.example.homebudget.service.IncomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public ResponseEntity<Income> get(@PathVariable Long id) {
        return incomeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        incomeService.delete(id);
    }
}
