package com.example.homebudget.controller;

import com.example.homebudget.model.Expense;
import com.example.homebudget.repository.ExpenseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @GetMapping
    public List<Expense> list() {
        return expenseRepository.findAll();
    }

    @PostMapping
    public Expense create(@RequestBody Expense expense) {
        return expenseRepository.save(expense);
    }

    @GetMapping("/summary")
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return expenseRepository.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> get(@PathVariable Long id) {
        return expenseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        expenseRepository.deleteById(id);
    }
}
