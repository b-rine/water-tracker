import { Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { WaterService } from './services/water-log.service';
import { WaterLog, DailyGoal, DailySummary } from './models/water-log.model';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-water-log',
  standalone: true,
  imports: [HttpClientModule, CommonModule, FormsModule],
  templateUrl: './water-log.component.html',
  styleUrls: ['./water-log.component.css']
})
export class WaterLogComponent implements OnInit {
  public showLogs: boolean = false;
  public showSettings: boolean = false;
  public waterLogs: WaterLog[] = [];
  public dailyGoal: DailyGoal | null = null;
  public dailyGoalOunces: number = 64;
  public newWaterLog: WaterLog = { id: 0, amountOunces: 0 };
  public totalOunces: number = 0;
  public progressPercentage: number = 0;
  public animatedProgressPercentage: number = 0;
  public selectedDate: string = '';
  public isLoading: boolean = false;
  public errorMessage: string = '';
  public newGoalAmount: number = 64;
  private animationInProgress: boolean = false;
  private targetProgressPercentage: number = 0;

  constructor(private waterService: WaterService) {
    this.selectedDate = this.formatDate(new Date());
  }

  ngOnInit() {
    this.loadDailySummary();
  }

  public loadDailySummary(): void {
    this.isLoading = true;
    this.errorMessage = '';
    
    this.waterService.getDailySummary().subscribe({
      next: (summary: DailySummary) => {
        this.waterLogs = summary.logs;
        this.dailyGoal = summary.goal;
        this.totalOunces = summary.totalOunces;
        this.progressPercentage = summary.progressPercentage;
        this.animatedProgressPercentage = summary.progressPercentage; // Initialize animated value
        this.targetProgressPercentage = summary.progressPercentage; // Initialize target value
        this.dailyGoalOunces = summary.goal.goalOunces;
        this.newGoalAmount = summary.goal.goalOunces;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = 'Failed to load daily summary';
        this.isLoading = false;
        console.error('Error loading summary:', error);
      }
    });
  }

  private loadDailySummaryWithoutAnimation(): void {
    this.waterService.getDailySummary().subscribe({
      next: (summary: DailySummary) => {
        this.waterLogs = summary.logs;
        this.dailyGoal = summary.goal;
        this.totalOunces = summary.totalOunces;
        this.progressPercentage = summary.progressPercentage;
        // Don't update animatedProgressPercentage - let animation continue
        this.dailyGoalOunces = summary.goal.goalOunces;
        this.newGoalAmount = summary.goal.goalOunces;
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = 'Failed to load daily summary';
        this.isLoading = false;
        console.error('Error loading summary:', error);
      }
    });
  }

  public loadLogsByDate(): void {
    if (!this.selectedDate) return;
    
    this.isLoading = true;
    this.waterService.getLogsByDate(this.selectedDate).subscribe({
      next: (logs: WaterLog[]) => {
        this.waterLogs = logs;
        this.calculateTotalOunces();
        this.animatedProgressPercentage = this.progressPercentage; // Initialize animated value
        this.targetProgressPercentage = this.progressPercentage; // Initialize target value
        this.isLoading = false;
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = 'Failed to load logs for selected date';
        this.isLoading = false;
      }
    });
  }

  public addLog(): void {
    if (this.newWaterLog.amountOunces <= 0) {
      this.errorMessage = 'Please enter a valid amount';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    // Calculate the new totals locally for smooth animation
    const newTotalOunces = this.totalOunces + this.newWaterLog.amountOunces;
    const newProgressPercentage = this.dailyGoalOunces > 0 ? 
      Math.round((newTotalOunces / this.dailyGoalOunces) * 100) : 0;

    // Update the displayed values immediately
    this.totalOunces = newTotalOunces;
    this.progressPercentage = newProgressPercentage;

    // Start the smooth animation
    this.animateWaterLevel(newProgressPercentage);

    this.waterService.addWaterLog(this.newWaterLog).subscribe({
      next: (response: WaterLog) => {
        // Reload the daily summary to sync with server (but don't animate again)
        this.loadDailySummaryWithoutAnimation();
        this.newWaterLog = { id: 0, amountOunces: 0 };
      },
      error: (error: HttpErrorResponse) => {
        // Revert the optimistic update on error
        this.totalOunces = this.totalOunces - this.newWaterLog.amountOunces;
        this.progressPercentage = this.dailyGoalOunces > 0 ? 
          Math.round((this.totalOunces / this.dailyGoalOunces) * 100) : 0;
        this.animatedProgressPercentage = this.progressPercentage;
        this.targetProgressPercentage = this.progressPercentage;
        
        this.errorMessage = 'Failed to add water log';
        this.isLoading = false;
      }
    });
  }

  public deleteLog(logId: number): void {
    if (!confirm('Are you sure you want to delete this log?')) {
      return;
    }

    // Find the log to get its amount for optimistic update
    const logToDelete = this.waterLogs.find(log => log.id === logId);
    if (!logToDelete) {
      this.errorMessage = 'Log not found';
      return;
    }

    this.isLoading = true;

    // Calculate the new totals locally for smooth animation
    const newTotalOunces = this.totalOunces - logToDelete.amountOunces;
    const newProgressPercentage = this.dailyGoalOunces > 0 ? 
      Math.round((newTotalOunces / this.dailyGoalOunces) * 100) : 0;

    // Update the displayed values immediately
    this.totalOunces = newTotalOunces;
    this.progressPercentage = newProgressPercentage;

    // Start the smooth animation
    this.animateWaterLevel(newProgressPercentage);

    this.waterService.deleteWaterLog(logId).subscribe({
      next: () => {
        // Reload the daily summary to sync with server (but don't animate again)
        this.loadDailySummaryWithoutAnimation();
      },
      error: (error: HttpErrorResponse) => {
        // Revert the optimistic update on error
        this.totalOunces = this.totalOunces + logToDelete.amountOunces;
        this.progressPercentage = this.dailyGoalOunces > 0 ? 
          Math.round((this.totalOunces / this.dailyGoalOunces) * 100) : 0;
        this.animatedProgressPercentage = this.progressPercentage;
        this.targetProgressPercentage = this.progressPercentage;
        
        this.errorMessage = 'Failed to delete water log';
        this.isLoading = false;
      }
    });
  }

  public updateGoal(): void {
    if (this.newGoalAmount <= 0) {
      this.errorMessage = 'Please enter a valid goal amount';
      return;
    }

    this.isLoading = true;
    this.waterService.updateTodaysGoal(this.newGoalAmount).subscribe({
      next: (goal: DailyGoal) => {
        this.dailyGoal = goal;
        this.dailyGoalOunces = goal.goalOunces;
        this.showSettings = false;
        this.loadDailySummary(); // Refresh to recalculate percentages
      },
      error: (error: HttpErrorResponse) => {
        this.errorMessage = 'Failed to update goal';
        this.isLoading = false;
      }
    });
  }

  private calculateTotalOunces(): void {
    this.totalOunces = this.waterLogs.reduce((total, log) => total + log.amountOunces, 0);
    this.progressPercentage = this.dailyGoalOunces > 0 ? 
      Math.round((this.totalOunces / this.dailyGoalOunces) * 100) : 0;
  }

  public toggleView(): void {
    this.showLogs = !this.showLogs;
  }

  public toggleSettings(): void {
    this.showSettings = !this.showSettings;
  }

  public formatTime(dateTimeString: string): string {
    if (!dateTimeString) return '';
    const date = new Date(dateTimeString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  public isToday(): boolean {
    const today = this.formatDate(new Date());
    return this.selectedDate === today;
  }

  public goToToday(): void {
    this.selectedDate = this.formatDate(new Date());
    this.loadDailySummary();
  }

  private formatDate(date: Date): string {
    return date.toISOString().split('T')[0];
  }

  public getProgressColor(): string {
    if (this.progressPercentage >= 100) return 'bg-green-500';
    if (this.progressPercentage >= 75) return 'bg-blue-500';
    if (this.progressPercentage >= 50) return 'bg-yellow-500';
    return 'bg-red-400';
  }

  public addQuickAmount(amount: number): void {
    this.newWaterLog.amountOunces = amount;
    this.addLog();
  }

  public getMath(): typeof Math {
    return Math;
  }

  private animateWaterLevel(targetPercentage: number): void {
    // If animation is in progress, update the target and let current animation continue to new target
    if (this.animationInProgress) {
      // Just update the target - the current animation will smoothly transition to the new target
      this.targetProgressPercentage = targetPercentage;
      return;
    }
    
    this.animationInProgress = true;
    this.targetProgressPercentage = targetPercentage;
    const startPercentage = this.animatedProgressPercentage;
    const duration = 1000; // 1 second animation
    const startTime = Date.now();

    const animate = () => {
      const elapsed = Date.now() - startTime;
      const progress = Math.min(elapsed / duration, 1);
      
      // Calculate current difference to target (may have changed during animation)
      const currentDifference = this.targetProgressPercentage - startPercentage;
      
      // Use easeOutCubic for smooth animation
      const easeOutCubic = 1 - Math.pow(1 - progress, 3);
      
      this.animatedProgressPercentage = startPercentage + (currentDifference * easeOutCubic);
      
      if (progress < 1) {
        requestAnimationFrame(animate);
      } else {
        this.animatedProgressPercentage = this.targetProgressPercentage;
        this.animationInProgress = false;
      }
    };

    requestAnimationFrame(animate);
  }
}
