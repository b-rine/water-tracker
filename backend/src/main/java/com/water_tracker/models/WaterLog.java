package com.water_tracker.models;

// import java.time.LocalDateTime;

import java.math.BigDecimal;


public class WaterLog {

    // private LocalDateTime dateAndTime;
    private Integer id;
    private BigDecimal amountOunces;

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
}
