package com.water_tracker.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.water_tracker.models.WaterLog;

public class WaterLogRowMapper implements RowMapper<WaterLog> {

    @Override
    public WaterLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        WaterLog log = new WaterLog();
        log.setAmountLiters(rs.getBigDecimal("liters"));
        return log;
    }
}
