package com.example.homebudget.service;

import com.example.homebudget.model.Income;
import com.example.homebudget.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class IncomeService {
    private final IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    public List<Income> findAll() {
        return repository.findAll();
    }

    public Optional<Income> findById(Long id) {
        return repository.findById(id);
    }

    public Income save(Income income) {
        return repository.save(income);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public BigDecimal sumByMonthAndYear(int year, int month) {
        return repository.sumByMonthAndYear(year, month);
    }

    public BigDecimal cumulativeSumUpToMonth(int year, int month) {
        return repository.cumulativeSumUpToMonth(year, month);
    }

    public List<Object[]> sumByMonthForYear(int year) {
        return repository.sumByMonthForYear(year);
    }

    public List<Integer> availableYears() {
        return repository.findYears();
    }

    public List<Integer> availableMonthsForYear(int year) {
        return repository.findMonthsForYear(year);
    }
}
