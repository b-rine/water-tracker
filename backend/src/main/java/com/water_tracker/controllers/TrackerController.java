package com.water_tracker.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.water_tracker.models.WaterLog;
import com.water_tracker.models.DailyGoal;
import com.water_tracker.services.WaterLogService;
import com.water_tracker.services.DailyGoalService;

@RestController
@RequestMapping("/tracker")
public class TrackerController {

    private final WaterLogService waterLogService;
    private final DailyGoalService dailyGoalService;

    public TrackerController(WaterLogService waterLogService, DailyGoalService dailyGoalService) {
        this.waterLogService = waterLogService;
        this.dailyGoalService = dailyGoalService;
    }

    @GetMapping
    public List<WaterLog> getWaterLogs() {
        return waterLogService.findAllLogs();
    }

    @GetMapping("/today")
    public List<WaterLog> getTodaysLogs() {
        return waterLogService.findTodaysLogs();
    }

    @GetMapping("/date/{date}")
    public List<WaterLog> getLogsByDate(@PathVariable("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return waterLogService.findLogsByDate(localDate);
    }

    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getDailySummary() {
        Map<String, Object> summary = new HashMap<>();
        
        DailyGoal todaysGoal = dailyGoalService.getTodaysGoal();
        BigDecimal totalOunces = waterLogService.getTotalOuncesForToday();
        List<WaterLog> todaysLogs = waterLogService.findTodaysLogs();
        
        summary.put("goal", todaysGoal);
        summary.put("totalOunces", totalOunces);
        summary.put("logs", todaysLogs);
        summary.put("progressPercentage", 
            todaysGoal.getGoalOunces().compareTo(BigDecimal.ZERO) > 0 ? 
            totalOunces.multiply(BigDecimal.valueOf(100)).divide(todaysGoal.getGoalOunces(), 2, java.math.RoundingMode.HALF_UP) : 
            BigDecimal.ZERO);
        
        return ResponseEntity.ok(summary);
    }

    @PostMapping
    public ResponseEntity<WaterLog> addWaterLog(@RequestBody WaterLog log) {
        try {
            waterLogService.addWaterLog(log);
            return ResponseEntity.ok(log);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaterLog(@PathVariable("id") Integer id) {
        try {
            waterLogService.deleteWaterLog(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Daily Goal endpoints
    @GetMapping("/goal")
    public DailyGoal getTodaysGoal() {
        return dailyGoalService.getTodaysGoal();
    }

    @PostMapping("/goal")
    public ResponseEntity<DailyGoal> updateTodaysGoal(@RequestBody Map<String, BigDecimal> request) {
        try {
            BigDecimal goalOunces = request.get("goalOunces");
            if (goalOunces == null || goalOunces.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().build();
            }
            DailyGoal updated = dailyGoalService.updateTodaysGoal(goalOunces);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
