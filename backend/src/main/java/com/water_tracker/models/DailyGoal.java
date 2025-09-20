package com.water_tracker.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DailyGoal {

    private Integer id;
    private BigDecimal goalOunces;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate goalDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    // Constructors
    public DailyGoal() {}

    public DailyGoal(LocalDate goalDate, BigDecimal goalOunces) {
        this.goalDate = goalDate;
        this.goalOunces = goalOunces;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getGoalOunces() {
        return this.goalOunces;
    }

    public void setGoalOunces(BigDecimal goalOunces) {
        this.goalOunces = goalOunces;
    }

    public LocalDate getGoalDate() {
        return this.goalDate;
    }

    public void setGoalDate(LocalDate goalDate) {
        this.goalDate = goalDate;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
