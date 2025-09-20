package com.water_tracker.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.water_tracker.models.WaterLog;

public class WaterLogRowMapper implements RowMapper<WaterLog> {

    @Override
    public WaterLog mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        WaterLog log = new WaterLog();
        log.setId(rs.getInt("id"));
        log.setAmountOunces(rs.getBigDecimal("ounces"));
        
        // Handle timestamp columns
        if (rs.getTimestamp("logged_at") != null) {
            log.setLoggedAt(rs.getTimestamp("logged_at").toLocalDateTime());
        }
        if (rs.getDate("log_date") != null) {
            log.setLogDate(rs.getDate("log_date").toLocalDate());
        }
        
        return log;
    }
}
