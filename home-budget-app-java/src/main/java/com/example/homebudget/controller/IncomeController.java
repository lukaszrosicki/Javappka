package com.example.homebudget.controller;

import com.example.homebudget.model.Income;
import com.example.homebudget.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    private final IncomeRepository incomeRepository;

    public IncomeController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @GetMapping
    public List<Income> list() {
        return incomeRepository.findAll();
    }

    @PostMapping
    public Income create(@RequestBody Income income) {
        return incomeRepository.save(income);
    }

    @GetMapping("/summary")
    public BigDecimal summary(@RequestParam int year, @RequestParam int month) {
        return incomeRepository.sumByMonthAndYear(year, month);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> get(@PathVariable Long id) {
        return incomeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        incomeRepository.deleteById(id);
    }
}
