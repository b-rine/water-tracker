package com.water_tracker.models;

// import java.time.LocalDateTime;

import java.math.BigDecimal;


public class WaterLog {

    // private LocalDateTime dateAndTime;
    private BigDecimal amountOunces;

    public BigDecimal getAmountOunces() {
        return this.amountOunces;
    }

    public void setAmountOunces(BigDecimal amountOunces) {
        this.amountOunces = amountOunces;
    }
}
