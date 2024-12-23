import { Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule for HTTP calls
import { WaterService } from './services/water-log.service'; // Import your service
import { WaterLog } from './models/water-log.model';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-water-log',
  standalone: true, // Declare it as a standalone component
  imports: [HttpClientModule, CommonModule, FormsModule], // Import necessary modules
  templateUrl: './water-log.component.html',
  styleUrls: ['./water-log.component.css']
})
export class WaterLogComponent implements OnInit {
  public showLogs: boolean = false;
  public waterLogs: WaterLog[] = [];
  public dailyGoalOunces: number = 64;
  public newWaterLog: WaterLog = { id: 0, amountOunces: 0 };
  public totalOunces: number = 0;

  constructor(private waterService: WaterService) {}

  ngOnInit() {
    this.getLogs();
  }

  public getLogs(): void {
    this.waterService.getWaterLogs().subscribe({
      next: (response: WaterLog[]) => {
        this.waterLogs = response;
        this.calculateTotalOunces()
        console.log(this.waterLogs);
      },
      error: (error: HttpErrorResponse) => {
        alert(error.message);
      },
      complete: () => {
        console.log('Fetching water logs completed.');
      }
    });
  }

  public addLog(): void {
    this.waterService.addWaterLog(this.newWaterLog).subscribe({
      next: (response: WaterLog) => {
        this.waterLogs.push(response);
        this.calculateTotalOunces();
        this.newWaterLog = { id: 0, amountOunces: 0 };
      },
      error (error: HttpErrorResponse) {
        alert(error.message);
      },
      complete: () => {
        console.log('Adding water log completed.');
      }
    });
  }

  public deleteLog(logId: number): void {
    this.waterService.deleteWaterLog(logId).subscribe({
      next: () => {
        this.waterLogs = this.waterLogs.filter(log => log.id !== logId);
        this.calculateTotalOunces();
      },
      error (error: HttpErrorResponse) {
        alert(error.message);
      },
      complete: () => {
        console.log('Deleting water log completed.');
      }
    });
  }

  private calculateTotalOunces(): void {
    this.totalOunces = this.waterLogs.reduce((total, log) => total + log.amountOunces, 0);
  }

  public toggleView(): void {
    this.showLogs = !this.showLogs;
  }
}
