package com.water_tracker.models;

// import java.time.LocalDateTime;

import java.math.BigDecimal;


public class WaterLog {

    // private LocalDateTime dateAndTime;
    private BigDecimal amountLiters;

    public BigDecimal getAmountLiters() {
        return this.amountLiters;
    }

    public void setAmountLiters(BigDecimal amountLiters) {
        this.amountLiters = amountLiters;
    }
}
