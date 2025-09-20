package com.water_tracker.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import com.water_tracker.models.DailyGoal;

public class DailyGoalRowMapper implements RowMapper<DailyGoal> {

    @Override
    public DailyGoal mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        DailyGoal goal = new DailyGoal();
        goal.setId(rs.getInt("id"));
        goal.setGoalOunces(rs.getBigDecimal("goal_ounces"));
        
        if (rs.getDate("goal_date") != null) {
            goal.setGoalDate(rs.getDate("goal_date").toLocalDate());
        }
        if (rs.getTimestamp("created_at") != null) {
            goal.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        
        return goal;
    }
}
