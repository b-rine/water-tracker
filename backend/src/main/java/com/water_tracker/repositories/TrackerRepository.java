package com.water_tracker.repositories;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

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
        String sql = "SELECT * FROM water_db ORDER BY logged_at DESC"; 
        return jdbc.query(sql, new WaterLogRowMapper());
    }

    public List<WaterLog> findLogsByDate(LocalDate date) {
        String sql = "SELECT * FROM water_db WHERE log_date = ? ORDER BY logged_at DESC";
        return jdbc.query(sql, new WaterLogRowMapper(), date);
    }

    public List<WaterLog> findLogsBetweenDates(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM water_db WHERE log_date BETWEEN ? AND ? ORDER BY logged_at DESC";
        return jdbc.query(sql, new WaterLogRowMapper(), startDate, endDate);
    }

    public BigDecimal getTotalOuncesForDate(LocalDate date) {
        String sql = "SELECT COALESCE(SUM(ounces), 0) FROM water_db WHERE log_date = ?";
        return jdbc.queryForObject(sql, BigDecimal.class, date);
    }

    public void addWaterLog(WaterLog log) {
        String sql = "INSERT INTO water_db (ounces, logged_at, log_date) VALUES (?, ?, ?)";
        jdbc.update(sql, log.getAmountOunces(), log.getLoggedAt(), log.getLogDate());
    }

    public void deleteWaterLog(Integer id) {
        String sql = "DELETE FROM water_db WHERE id = ?";
        jdbc.update(sql, id);
    }
}
