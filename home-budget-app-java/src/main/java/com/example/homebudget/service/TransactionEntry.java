package com.example.homebudget.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionEntry {
    private Long id;
    private String type;
    private String category;
    private LocalDate date;
    private BigDecimal amount;
    private String notes;

    public TransactionEntry(Long id, String type, String category, LocalDate date, BigDecimal amount, String notes) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }
}
