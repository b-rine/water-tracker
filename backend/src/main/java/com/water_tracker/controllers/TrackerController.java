package com.water_tracker.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.water_tracker.models.WaterLog;
import com.water_tracker.services.WaterLogService;

@RestController
@RequestMapping("/tracker")
public class TrackerController {

    private final WaterLogService waterLogService;

    public TrackerController(WaterLogService waterLogService) {
        this.waterLogService = waterLogService;
    }

    @GetMapping
    public List<WaterLog> getWaterLogs() {
        return waterLogService.findAllLogs();
    }

    @PostMapping
    public void addWaterLog(@RequestBody WaterLog log) {
        waterLogService.addWaterLog(log);
    }
    
}
