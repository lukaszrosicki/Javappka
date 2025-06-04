package com.example.homebudget.repository;

import com.example.homebudget.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE YEAR(i.date)=:year AND MONTH(i.date)=:month")
    BigDecimal sumByMonthAndYear(@Param("year") int year, @Param("month") int month);
}
