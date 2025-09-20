package com.water_tracker.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class WaterLog {

    private Integer id;
    private BigDecimal amountOunces;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loggedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate logDate;

    // Constructors
    public WaterLog() {}

    public WaterLog(BigDecimal amountOunces) {
        this.amountOunces = amountOunces;
        this.loggedAt = LocalDateTime.now();
        this.logDate = LocalDate.now();
    }

    // Getters and Setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmountOunces() {
        return this.amountOunces;
    }

    public void setAmountOunces(BigDecimal amountOunces) {
        this.amountOunces = amountOunces;
    }

    public LocalDateTime getLoggedAt() {
        return this.loggedAt;
    }

    public void setLoggedAt(LocalDateTime loggedAt) {
        this.loggedAt = loggedAt;
    }

    public LocalDate getLogDate() {
        return this.logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }
}
