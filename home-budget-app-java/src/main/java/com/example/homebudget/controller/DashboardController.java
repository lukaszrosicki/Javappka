package com.example.homebudget.controller;

import com.example.homebudget.service.DashboardService;
import com.example.homebudget.service.TransactionEntry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping
    public String dashboard(@RequestParam(required = false) Integer year,
                            @RequestParam(required = false) Integer month,
                            Model model) {
        List<TransactionEntry> list = service.allTransactions();
        model.addAttribute("transactions", list);
        Set<Integer> years = service.availableYears();
        model.addAttribute("years", years);
        if (year == null && !years.isEmpty()) {
            year = years.iterator().next();
        }
        if (year != null) {
            List<Integer> months = service.availableMonths(year);
            model.addAttribute("months", months);
            if (month == null && !months.isEmpty()) {
                month = months.get(0);
            }
        }
        if (year != null && month != null) {
            BigDecimal inc = service.incomeSum(year, month);
            BigDecimal exp = service.expenseSum(year, month);
            model.addAttribute("incomeSum", inc);
            model.addAttribute("expenseSum", exp);
            model.addAttribute("incomeCum", service.incomeCumulative(year, month));
            model.addAttribute("expenseCum", service.expenseCumulative(year, month));
            Map<String, BigDecimal> cat = service.expenseByCategory(year, month);
            model.addAttribute("catLabels", cat.keySet());
            model.addAttribute("catData", cat.values());
            Map<Integer, BigDecimal> expYear = service.expenseByMonth(year);
            Map<Integer, BigDecimal> incYear = service.incomeByMonth(year);
            model.addAttribute("monthLabels", expYear.keySet());
            model.addAttribute("expYearData", expYear.values());
            model.addAttribute("incYearData", incYear.values());
        }
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        return "dashboard";
    }
}
