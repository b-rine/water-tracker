import { Component, OnInit } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule for HTTP calls
import { WaterService } from './water-log.service'; // Import your service
import { WaterLog } from './water-log.interface';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-water-log',
  standalone: true, // Declare it as a standalone component
  imports: [HttpClientModule, CommonModule], // Import necessary modules
  templateUrl: './water-log.component.html',
  styleUrls: ['./water-log.component.css']
})
export class WaterLogComponent implements OnInit {
  public waterLogs: WaterLog[] = [];

  constructor(private waterService: WaterService) {}

  ngOnInit() {
    this.getWaterLogs();
  }

  public getWaterLogs(): void {
    this.waterService.getWater().subscribe(
      (response: WaterLog[]) => {
        this.waterLogs = response;
        console.log(this.waterLogs);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
