package com.example.homebudget.controller;

import com.example.homebudget.model.Income;
import com.example.homebudget.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@Controller
@RequestMapping("/incomes")
public class IncomeController {

    private final IncomeRepository incomeRepository;

    public IncomeController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("incomes", incomeRepository.findAll());
        model.addAttribute("income", new Income());
        return "incomes";
    }

    @PostMapping
    public String create(@ModelAttribute Income income) {
        incomeRepository.save(income);
        return "redirect:/incomes";
    }

    @GetMapping("/summary")
    @ResponseBody
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return incomeRepository.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Income> get(@PathVariable Long id) {
        return incomeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        incomeRepository.deleteById(id);
    }
}
