package com.water_tracker.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.water_tracker.models.DailyGoal;

@Repository
public class DailyGoalRepository {

    private final JdbcTemplate jdbc;

    public DailyGoalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<DailyGoal> findAll() {
        String sql = "SELECT * FROM daily_goals ORDER BY goal_date DESC";
        return jdbc.query(sql, new DailyGoalRowMapper());
    }

    public Optional<DailyGoal> findByDate(LocalDate date) {
        String sql = "SELECT * FROM daily_goals WHERE goal_date = ?";
        List<DailyGoal> goals = jdbc.query(sql, new DailyGoalRowMapper(), date);
        return goals.isEmpty() ? Optional.empty() : Optional.of(goals.get(0));
    }

    public DailyGoal save(DailyGoal goal) {
        if (goal.getId() == null) {
            // Insert new goal
            String sql = "INSERT INTO daily_goals (goal_date, goal_ounces) VALUES (?, ?)";
            jdbc.update(sql, goal.getGoalDate(), goal.getGoalOunces());
            
            // Get the generated ID
            String selectSql = "SELECT * FROM daily_goals WHERE goal_date = ?";
            List<DailyGoal> saved = jdbc.query(selectSql, new DailyGoalRowMapper(), goal.getGoalDate());
            return saved.isEmpty() ? goal : saved.get(0);
        } else {
            // Update existing goal
            String sql = "UPDATE daily_goals SET goal_ounces = ? WHERE id = ?";
            jdbc.update(sql, goal.getGoalOunces(), goal.getId());
            return goal;
        }
    }

    public void deleteById(Integer id) {
        String sql = "DELETE FROM daily_goals WHERE id = ?";
        jdbc.update(sql, id);
    }

    public DailyGoal getTodaysGoal() {
        LocalDate today = LocalDate.now();
        Optional<DailyGoal> existing = findByDate(today);
        
        if (existing.isPresent()) {
            return existing.get();
        } else {
            // Create default goal for today
            DailyGoal defaultGoal = new DailyGoal(today, java.math.BigDecimal.valueOf(64.0));
            return save(defaultGoal);
        }
    }
}
