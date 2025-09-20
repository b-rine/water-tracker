import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WaterLog, DailyGoal, DailySummary} from '../models/water-log.model';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WaterService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getWaterLogs(): Observable<WaterLog[]> {
    return this.http.get<WaterLog[]>(`${this.apiServerUrl}/tracker`);
  }

  public getTodaysLogs(): Observable<WaterLog[]> {
    return this.http.get<WaterLog[]>(`${this.apiServerUrl}/tracker/today`);
  }

  public getLogsByDate(date: string): Observable<WaterLog[]> {
    return this.http.get<WaterLog[]>(`${this.apiServerUrl}/tracker/date/${date}`);
  }

  public getDailySummary(): Observable<DailySummary> {
    return this.http.get<DailySummary>(`${this.apiServerUrl}/tracker/summary`);
  }

  public addWaterLog(waterLog: WaterLog): Observable<WaterLog> {
    return this.http.post<WaterLog>(`${this.apiServerUrl}/tracker`, waterLog);
  }

  public deleteWaterLog(logId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/tracker/${logId}`);
  }

  public getTodaysGoal(): Observable<DailyGoal> {
    return this.http.get<DailyGoal>(`${this.apiServerUrl}/tracker/goal`);
  }

  public updateTodaysGoal(goalOunces: number): Observable<DailyGoal> {
    return this.http.post<DailyGoal>(`${this.apiServerUrl}/tracker/goal`, { goalOunces });
  }
}
