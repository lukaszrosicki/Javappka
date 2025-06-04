package com.example.homebudget;

import com.example.homebudget.model.ApplicationUser;
import com.example.homebudget.model.Expense;
import com.example.homebudget.model.ExpenseCategory;
import com.example.homebudget.model.Income;
import com.example.homebudget.model.IncomeCategory;
import com.example.homebudget.repository.ApplicationUserRepository;
import com.example.homebudget.repository.ExpenseRepository;
import com.example.homebudget.repository.IncomeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ApplicationUserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    public DataSeeder(ApplicationUserRepository userRepository,
                      ExpenseRepository expenseRepository,
                      IncomeRepository incomeRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            ApplicationUser user = new ApplicationUser();
            user.setEmail("demo@demo.com");
            user.setPassword("password");
            userRepository.save(user);

            Expense expense = new Expense();
            expense.setCategory(ExpenseCategory.FOOD);
            expense.setDate(LocalDate.now());
            expense.setAmount(BigDecimal.valueOf(100));
            expense.setUser(user);
            expenseRepository.save(expense);

            Income income = new Income();
            income.setCategory(IncomeCategory.SALARY);
            income.setDate(LocalDate.now());
            income.setAmount(BigDecimal.valueOf(500));
            income.setUser(user);
            incomeRepository.save(income);
        }
    }
}
