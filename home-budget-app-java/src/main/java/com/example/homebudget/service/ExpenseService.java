package com.example.homebudget.service;

import com.example.homebudget.model.Expense;
import com.example.homebudget.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ExpenseService {
    private final ExpenseRepository repository;

    public ExpenseService(ExpenseRepository repository) {
        this.repository = repository;
    }

    public List<Expense> findAll() {
        return repository.findAll();
    }

    public Optional<Expense> findById(Long id) {
        return repository.findById(id);
    }

    public Expense save(Expense expense) {
        return repository.save(expense);
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

    public List<Object[]> sumByCategory(int year, int month) {
        return repository.sumByCategory(year, month);
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
