package com.example.homebudget.repository;

import com.example.homebudget.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE YEAR(i.date)=:year AND MONTH(i.date)=:month")
    BigDecimal sumByMonthAndYear(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COALESCE(SUM(i.amount),0) FROM Income i WHERE YEAR(i.date)=:year AND MONTH(i.date)<=:month")
    BigDecimal cumulativeSumUpToMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT MONTH(i.date), SUM(i.amount) FROM Income i WHERE YEAR(i.date)=:year GROUP BY MONTH(i.date) ORDER BY MONTH(i.date)")
    List<Object[]> sumByMonthForYear(@Param("year") int year);

    @Query("SELECT DISTINCT YEAR(i.date) FROM Income i ORDER BY YEAR(i.date)")
    List<Integer> findYears();

    @Query("SELECT DISTINCT MONTH(i.date) FROM Income i WHERE YEAR(i.date)=:year ORDER BY MONTH(i.date)")
    List<Integer> findMonthsForYear(@Param("year") int year);
}
