package com.water_tracker.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

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
    public WaterLog addWaterLog(@RequestBody WaterLog log) {
        waterLogService.addWaterLog(log);
        return log;
    }

    @DeleteMapping("/{id}")
    public void deleteWaterLog(@PathVariable("id") Integer id) {
        waterLogService.deleteWaterLog(id);
    }
}
