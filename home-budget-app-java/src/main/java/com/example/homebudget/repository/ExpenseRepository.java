package com.example.homebudget.repository;

import com.example.homebudget.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE YEAR(e.date)=:year AND MONTH(e.date)=:month")
    BigDecimal sumByMonthAndYear(@Param("year") int year, @Param("month") int month);
}
