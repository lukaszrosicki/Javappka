package com.example.homebudget.repository;

import com.example.homebudget.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE YEAR(e.date)=:year AND MONTH(e.date)=:month")
    BigDecimal sumByMonthAndYear(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COALESCE(SUM(e.amount),0) FROM Expense e WHERE YEAR(e.date)=:year AND MONTH(e.date)<=:month")
    BigDecimal cumulativeSumUpToMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE YEAR(e.date)=:year AND MONTH(e.date)=:month GROUP BY e.category")
    List<Object[]> sumByCategory(@Param("year") int year, @Param("month") int month);

    @Query("SELECT MONTH(e.date), SUM(e.amount) FROM Expense e WHERE YEAR(e.date)=:year GROUP BY MONTH(e.date) ORDER BY MONTH(e.date)")
    List<Object[]> sumByMonthForYear(@Param("year") int year);

    @Query("SELECT DISTINCT YEAR(e.date) FROM Expense e ORDER BY YEAR(e.date)")
    List<Integer> findYears();

    @Query("SELECT DISTINCT MONTH(e.date) FROM Expense e WHERE YEAR(e.date)=:year ORDER BY MONTH(e.date)")
    List<Integer> findMonthsForYear(@Param("year") int year);
}
