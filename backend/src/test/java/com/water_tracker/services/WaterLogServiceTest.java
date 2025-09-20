package com.water_tracker.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.water_tracker.models.WaterLog;
import com.water_tracker.repositories.TrackerRepository;

@ExtendWith(MockitoExtension.class)
class WaterLogServiceTest {

    @Mock
    private TrackerRepository trackerRepository;

    private WaterLogService waterLogService;

    @BeforeEach
    void setUp() {
        waterLogService = new WaterLogService(trackerRepository);
    }

    @Test
    void testFindAllLogs() {
        // Given
        List<WaterLog> expectedLogs = Arrays.asList(
            createWaterLog(1, BigDecimal.valueOf(16.0)),
            createWaterLog(2, BigDecimal.valueOf(12.0))
        );
        when(trackerRepository.findAllLogs()).thenReturn(expectedLogs);

        // When
        List<WaterLog> actualLogs = waterLogService.findAllLogs();

        // Then
        assertEquals(expectedLogs, actualLogs);
        verify(trackerRepository).findAllLogs();
    }

    @Test
    void testFindTodaysLogs() {
        // Given
        List<WaterLog> expectedLogs = Arrays.asList(
            createWaterLog(1, BigDecimal.valueOf(16.0))
        );
        when(trackerRepository.findLogsByDate(LocalDate.now())).thenReturn(expectedLogs);

        // When
        List<WaterLog> actualLogs = waterLogService.findTodaysLogs();

        // Then
        assertEquals(expectedLogs, actualLogs);
        verify(trackerRepository).findLogsByDate(LocalDate.now());
    }

    @Test
    void testGetTotalOuncesForToday() {
        // Given
        BigDecimal expectedTotal = BigDecimal.valueOf(32.0);
        when(trackerRepository.getTotalOuncesForDate(LocalDate.now())).thenReturn(expectedTotal);

        // When
        BigDecimal actualTotal = waterLogService.getTotalOuncesForToday();

        // Then
        assertEquals(expectedTotal, actualTotal);
        verify(trackerRepository).getTotalOuncesForDate(LocalDate.now());
    }

    @Test
    void testAddWaterLog_ValidLog() {
        // Given
        WaterLog log = new WaterLog();
        log.setAmountOunces(BigDecimal.valueOf(16.0));

        // When
        assertDoesNotThrow(() -> waterLogService.addWaterLog(log));

        // Then
        assertNotNull(log.getLoggedAt());
        assertNotNull(log.getLogDate());
        verify(trackerRepository).addWaterLog(log);
    }

    @Test
    void testAddWaterLog_InvalidAmount_ThrowsException() {
        // Given
        WaterLog log = new WaterLog();
        log.setAmountOunces(BigDecimal.valueOf(-5.0));

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> waterLogService.addWaterLog(log)
        );
        assertEquals("Water amount must be greater than 0", exception.getMessage());
        verify(trackerRepository, never()).addWaterLog(any());
    }

    @Test
    void testAddWaterLog_NullAmount_ThrowsException() {
        // Given
        WaterLog log = new WaterLog();
        log.setAmountOunces(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> waterLogService.addWaterLog(log)
        );
        assertEquals("Water amount must be greater than 0", exception.getMessage());
        verify(trackerRepository, never()).addWaterLog(any());
    }

    @Test
    void testDeleteWaterLog_ValidId() {
        // Given
        Integer logId = 1;

        // When
        assertDoesNotThrow(() -> waterLogService.deleteWaterLog(logId));

        // Then
        verify(trackerRepository).deleteWaterLog(logId);
    }

    @Test
    void testDeleteWaterLog_InvalidId_ThrowsException() {
        // Given
        Integer logId = -1;

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> waterLogService.deleteWaterLog(logId)
        );
        assertEquals("Invalid log ID", exception.getMessage());
        verify(trackerRepository, never()).deleteWaterLog(any());
    }

    @Test
    void testDeleteWaterLog_NullId_ThrowsException() {
        // Given
        Integer logId = null;

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, 
            () -> waterLogService.deleteWaterLog(logId)
        );
        assertEquals("Invalid log ID", exception.getMessage());
        verify(trackerRepository, never()).deleteWaterLog(any());
    }

    private WaterLog createWaterLog(Integer id, BigDecimal amount) {
        WaterLog log = new WaterLog();
        log.setId(id);
        log.setAmountOunces(amount);
        log.setLoggedAt(LocalDateTime.now());
        log.setLogDate(LocalDate.now());
        return log;
    }
}
