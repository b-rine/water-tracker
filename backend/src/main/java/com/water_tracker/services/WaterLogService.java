package com.water_tracker.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.water_tracker.models.WaterLog;
import com.water_tracker.repositories.TrackerRepository;

@Service
public class WaterLogService {

    private static final Logger logger = LoggerFactory.getLogger(WaterLogService.class);
    private final TrackerRepository trackerRepository;

    public WaterLogService(TrackerRepository trackerRepository) {
        this.trackerRepository = trackerRepository;
    }

    public List<WaterLog> findAllLogs() {
        return trackerRepository.findAllLogs();
    }

    public List<WaterLog> findTodaysLogs() {
        return trackerRepository.findLogsByDate(LocalDate.now());
    }

    public List<WaterLog> findLogsByDate(LocalDate date) {
        return trackerRepository.findLogsByDate(date);
    }

    public List<WaterLog> findLogsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return trackerRepository.findLogsBetweenDates(startDate, endDate);
    }

    public BigDecimal getTotalOuncesForToday() {
        return trackerRepository.getTotalOuncesForDate(LocalDate.now());
    }

    public BigDecimal getTotalOuncesForDate(LocalDate date) {
        return trackerRepository.getTotalOuncesForDate(date);
    }

    public void addWaterLog(WaterLog log) {
        // Set timestamps if not provided
        if (log.getLoggedAt() == null) {
            log.setLoggedAt(LocalDateTime.now());
        }
        if (log.getLogDate() == null) {
            log.setLogDate(LocalDate.now());
        }
        
        // Validate amount
        if (log.getAmountOunces() == null || log.getAmountOunces().compareTo(BigDecimal.ZERO) <= 0) {
            logger.warn("Attempt to add water log with invalid amount: {}", log.getAmountOunces());
            throw new IllegalArgumentException("Water amount must be greater than 0");
        }
        
        logger.info("Adding water log: {} ounces at {}", log.getAmountOunces(), log.getLoggedAt());
        trackerRepository.addWaterLog(log);
        logger.info("Successfully added water log with ID: {}", log.getId());
    }

    public void deleteWaterLog(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Attempt to delete water log with invalid ID: {}", id);
            throw new IllegalArgumentException("Invalid log ID");
        }
        
        logger.info("Deleting water log with ID: {}", id);
        trackerRepository.deleteWaterLog(id);
        logger.info("Successfully deleted water log with ID: {}", id);
    }
}
