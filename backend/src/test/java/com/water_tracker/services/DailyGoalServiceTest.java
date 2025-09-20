package com.water_tracker.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.water_tracker.models.DailyGoal;
import com.water_tracker.repositories.DailyGoalRepository;

@ExtendWith(MockitoExtension.class)
class DailyGoalServiceTest {

    @Mock
    private DailyGoalRepository dailyGoalRepository;

    private DailyGoalService dailyGoalService;

    @BeforeEach
    void setUp() {
        dailyGoalService = new DailyGoalService(dailyGoalRepository);
    }

    @Test
    void testGetTodaysGoal() {
        // Given
        DailyGoal expectedGoal = createDailyGoal(1, LocalDate.now(), BigDecimal.valueOf(64.0));
        when(dailyGoalRepository.getTodaysGoal()).thenReturn(expectedGoal);

        // When
        DailyGoal actualGoal = dailyGoalService.getTodaysGoal();

        // Then
        assertEquals(expectedGoal, actualGoal);
        verify(dailyGoalRepository).getTodaysGoal();
    }

    @Test
    void testGetGoalByDate() {
        // Given
        LocalDate testDate = LocalDate.of(2023, 12, 25);
        DailyGoal expectedGoal = createDailyGoal(1, testDate, BigDecimal.valueOf(80.0));
        when(dailyGoalRepository.findByDate(testDate)).thenReturn(Optional.of(expectedGoal));

        // When
        Optional<DailyGoal> actualGoal = dailyGoalService.getGoalByDate(testDate);

        // Then
        assertTrue(actualGoal.isPresent());
        assertEquals(expectedGoal, actualGoal.get());
        verify(dailyGoalRepository).findByDate(testDate);
    }

    @Test
    void testSaveGoal_WithValidData() {
        // Given
        DailyGoal goal = new DailyGoal();
        goal.setGoalDate(LocalDate.now());
        goal.setGoalOunces(BigDecimal.valueOf(72.0));
        
        when(dailyGoalRepository.save(goal)).thenReturn(goal);

        // When
        DailyGoal savedGoal = dailyGoalService.saveGoal(goal);

        // Then
        assertEquals(goal, savedGoal);
        verify(dailyGoalRepository).save(goal);
    }

    @Test
    void testSaveGoal_WithNullDate_SetsToday() {
        // Given
        DailyGoal goal = new DailyGoal();
        goal.setGoalOunces(BigDecimal.valueOf(72.0));
        // goalDate is null
        
        when(dailyGoalRepository.save(goal)).thenReturn(goal);

        // When
        DailyGoal savedGoal = dailyGoalService.saveGoal(goal);

        // Then
        assertEquals(LocalDate.now(), savedGoal.getGoalDate());
        verify(dailyGoalRepository).save(goal);
    }

    @Test
    void testSaveGoal_WithInvalidAmount_SetsDefault() {
        // Given
        DailyGoal goal = new DailyGoal();
        goal.setGoalDate(LocalDate.now());
        goal.setGoalOunces(BigDecimal.valueOf(-10.0)); // Invalid amount
        
        when(dailyGoalRepository.save(goal)).thenReturn(goal);

        // When
        DailyGoal savedGoal = dailyGoalService.saveGoal(goal);

        // Then
        assertEquals(BigDecimal.valueOf(64.0), savedGoal.getGoalOunces());
        verify(dailyGoalRepository).save(goal);
    }

    @Test
    void testUpdateTodaysGoal() {
        // Given
        BigDecimal newGoalAmount = BigDecimal.valueOf(80.0);
        DailyGoal existingGoal = createDailyGoal(1, LocalDate.now(), BigDecimal.valueOf(64.0));
        DailyGoal updatedGoal = createDailyGoal(1, LocalDate.now(), newGoalAmount);
        
        when(dailyGoalRepository.getTodaysGoal()).thenReturn(existingGoal);
        when(dailyGoalRepository.save(existingGoal)).thenReturn(updatedGoal);

        // When
        DailyGoal result = dailyGoalService.updateTodaysGoal(newGoalAmount);

        // Then
        assertEquals(newGoalAmount, result.getGoalOunces());
        verify(dailyGoalRepository).getTodaysGoal();
        verify(dailyGoalRepository).save(existingGoal);
    }

    @Test
    void testDeleteGoal() {
        // Given
        Integer goalId = 1;

        // When
        dailyGoalService.deleteGoal(goalId);

        // Then
        verify(dailyGoalRepository).deleteById(goalId);
    }

    private DailyGoal createDailyGoal(Integer id, LocalDate date, BigDecimal goalOunces) {
        DailyGoal goal = new DailyGoal();
        goal.setId(id);
        goal.setGoalDate(date);
        goal.setGoalOunces(goalOunces);
        return goal;
    }
}
