package com.water_tracker.repositories;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public WaterLog addWaterLog(WaterLog log) {
        String sql = "INSERT INTO water_db (ounces) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setBigDecimal(1, log.getAmountOunces());
            return ps;
        }, keyHolder);

        Number generateId = keyHolder.getKey();
        if (generateId != null) {
            log.setId(generateId.intValue());
        }
        return log;
    }

    public void deleteWaterLog(Integer id) {
        String sql = "DELETE FROM water_db WHERE id = ?";
        jdbc.update(sql, id);
    }
}
