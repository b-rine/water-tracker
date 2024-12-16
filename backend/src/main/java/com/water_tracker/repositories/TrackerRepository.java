package com.water_tracker.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.water_tracker.models.WaterLog;

@Repository
public class TrackerRepository {

    private final JdbcTemplate jdbc;

    public TrackerRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    public List<WaterLog> findAllLogs() {
        String sql = "SELECT * FROM water_db"; 
        return jdbc.query(sql, new WaterLogRowMapper());
    }

    public void addWaterLog(WaterLog log) {
        String sql = "INSERT INTO water_db (liters) VALUES (?)";
        jdbc.update(sql, log.getAmountLiters());
    }
}
