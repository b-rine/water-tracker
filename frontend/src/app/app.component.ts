import {Component, OnInit} from '@angular/core';
import {WaterLog} from './waterLog';
import {WaterService} from './water.service';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
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
