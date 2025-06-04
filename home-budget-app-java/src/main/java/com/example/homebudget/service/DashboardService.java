package com.example.homebudget.service;

import com.example.homebudget.model.Expense;
import com.example.homebudget.model.Income;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public DashboardService(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    public List<TransactionEntry> allTransactions() {
        List<TransactionEntry> list = new ArrayList<>();
        for (Expense e : expenseService.findAll()) {
            list.add(new TransactionEntry(e.getId(), "Wydatek", e.getCategory().name(), e.getDate(), e.getAmount(), e.getNotes()));
        }
        for (Income i : incomeService.findAll()) {
            list.add(new TransactionEntry(i.getId(), "Dochód", i.getCategory().name(), i.getDate(), i.getAmount(), i.getNotes()));
        }
        list.sort(Comparator.comparing(TransactionEntry::getDate));
        return list;
    }

    public BigDecimal expenseSum(int year, int month) {
        return expenseService.sumByMonthAndYear(year, month);
    }

    public BigDecimal incomeSum(int year, int month) {
        return incomeService.sumByMonthAndYear(year, month);
    }

    public BigDecimal expenseCumulative(int year, int month) {
        return expenseService.cumulativeSumUpToMonth(year, month);
    }

    public BigDecimal incomeCumulative(int year, int month) {
        return incomeService.cumulativeSumUpToMonth(year, month);
    }

    public Map<String, BigDecimal> expenseByCategory(int year, int month) {
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (Object[] row : expenseService.sumByCategory(year, month)) {
            map.put(row[0].toString(), (BigDecimal) row[1]);
        }
        return map;
    }

    public Map<Integer, BigDecimal> expenseByMonth(int year) {
        Map<Integer, BigDecimal> map = new LinkedHashMap<>();
        for (Object[] row : expenseService.sumByMonthForYear(year)) {
            map.put(((Number) row[0]).intValue(), (BigDecimal) row[1]);
        }
        return map;
    }

    public Map<Integer, BigDecimal> incomeByMonth(int year) {
        Map<Integer, BigDecimal> map = new LinkedHashMap<>();
        for (Object[] row : incomeService.sumByMonthForYear(year)) {
            map.put(((Number) row[0]).intValue(), (BigDecimal) row[1]);
        }
        return map;
    }

    public Set<Integer> availableYears() {
        Set<Integer> years = new TreeSet<>();
        years.addAll(expenseService.availableYears());
        years.addAll(incomeService.availableYears());
        return years;
    }

    public List<Integer> availableMonths(int year) {
        Set<Integer> months = new TreeSet<>();
        months.addAll(expenseService.availableMonthsForYear(year));
        months.addAll(incomeService.availableMonthsForYear(year));
        return new ArrayList<>(months);
    }
}
