package com.water_tracker.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.water_tracker.models.WaterLog;
import com.water_tracker.repositories.TrackerRepository;

@Service
public class WaterLogService {

    private final TrackerRepository trackerRepository;

    public WaterLogService(TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    public List<WaterLog> findAllLogs() {
        return trackerRepository.findAllLogs();
    }

    public WaterLog addWaterLog(WaterLog log) {
        return trackerRepository.addWaterLog(log);
    }

    public void deleteWaterLog(Integer id) {
        trackerRepository.deleteWaterLog(id);
    }

}
