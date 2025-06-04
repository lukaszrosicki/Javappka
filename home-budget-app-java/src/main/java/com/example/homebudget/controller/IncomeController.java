package com.example.homebudget.controller;

import com.example.homebudget.model.Income;
import com.example.homebudget.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
