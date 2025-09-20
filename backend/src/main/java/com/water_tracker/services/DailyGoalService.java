package com.water_tracker.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.water_tracker.models.DailyGoal;
import com.water_tracker.repositories.DailyGoalRepository;

@Service
public class DailyGoalService {

    private final DailyGoalRepository dailyGoalRepository;

    public DailyGoalService(DailyGoalRepository dailyGoalRepository) {
        this.dailyGoalRepository = dailyGoalRepository;
    }

    public List<DailyGoal> getAllGoals() {
        return dailyGoalRepository.findAll();
    }

    public Optional<DailyGoal> getGoalByDate(LocalDate date) {
        return dailyGoalRepository.findByDate(date);
    }

    public DailyGoal getTodaysGoal() {
        return dailyGoalRepository.getTodaysGoal();
    }

    public DailyGoal saveGoal(DailyGoal goal) {
        if (goal.getGoalDate() == null) {
            goal.setGoalDate(LocalDate.now());
        }
        if (goal.getGoalOunces() == null || goal.getGoalOunces().compareTo(BigDecimal.ZERO) <= 0) {
            goal.setGoalOunces(BigDecimal.valueOf(64.0));
        }
        return dailyGoalRepository.save(goal);
    }

    public DailyGoal updateTodaysGoal(BigDecimal newGoalOunces) {
        DailyGoal todaysGoal = getTodaysGoal();
        todaysGoal.setGoalOunces(newGoalOunces);
        return dailyGoalRepository.save(todaysGoal);
    }

    public void deleteGoal(Integer id) {
        dailyGoalRepository.deleteById(id);
    }
}
