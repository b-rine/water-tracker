import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { WaterLog, DailyGoal, DailySummary } from '../water-log/models/water-log.model';

@Injectable()
export class LocalStorageWaterService {
  private readonly STORAGE_KEY = 'waterTracker';

  constructor() {
    this.initializeStorage();
  }

  private initializeStorage(): void {
    if (!localStorage.getItem(this.STORAGE_KEY)) {
      const initialData = {
        logs: [],
        goals: [{
          id: 1,
          goalOunces: 64,
          goalDate: new Date().toISOString().split('T')[0],
          createdAt: new Date().toISOString()
        }]
      };
      localStorage.setItem(this.STORAGE_KEY, JSON.stringify(initialData));
    }
  }

  private getData(): any {
    return JSON.parse(localStorage.getItem(this.STORAGE_KEY) || '{}');
  }

  private saveData(data: any): void {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(data));
  }

  public getWaterLogs(): Observable<WaterLog[]> {
    const data = this.getData();
    return of(data.logs || []);
  }

  public getTodaysLogs(): Observable<WaterLog[]> {
    const data = this.getData();
    const today = new Date().toISOString().split('T')[0];
    const todaysLogs = (data.logs || []).filter((log: WaterLog) => log.logDate === today);
    return of(todaysLogs);
  }

  public getLogsByDate(date: string): Observable<WaterLog[]> {
    const data = this.getData();
    const logsForDate = (data.logs || []).filter((log: WaterLog) => log.logDate === date);
    return of(logsForDate);
  }

  public getDailySummary(): Observable<DailySummary> {
    const data = this.getData();
    const today = new Date().toISOString().split('T')[0];
    
    const todaysLogs = (data.logs || []).filter((log: WaterLog) => log.logDate === today);
    const todaysGoal = (data.goals || []).find((goal: DailyGoal) => goal.goalDate === today) || {
      id: Date.now(),
      goalOunces: 64,
      goalDate: today,
      createdAt: new Date().toISOString()
    };
    
    const totalOunces = todaysLogs.reduce((sum: number, log: WaterLog) => sum + log.amountOunces, 0);
    const progressPercentage = todaysGoal.goalOunces > 0 ? 
      Math.round((totalOunces / todaysGoal.goalOunces) * 100) : 0;

    return of({
      goal: todaysGoal,
      totalOunces,
      logs: todaysLogs,
      progressPercentage
    });
  }

  public addWaterLog(waterLog: WaterLog): Observable<WaterLog> {
    const data = this.getData();
    const newLog: WaterLog = {
      ...waterLog,
      id: Date.now(),
      loggedAt: new Date().toISOString(),
      logDate: new Date().toISOString().split('T')[0]
    };
    
    if (!data.logs) {
      data.logs = [];
    }
    data.logs.push(newLog);
    this.saveData(data);
    
    return of(newLog);
  }

  public deleteWaterLog(logId: number): Observable<void> {
    const data = this.getData();
    if (data.logs) {
      data.logs = data.logs.filter((log: WaterLog) => log.id !== logId);
      this.saveData(data);
    }
    
    return of(void 0);
  }

  public getTodaysGoal(): Observable<DailyGoal> {
    const data = this.getData();
    const today = new Date().toISOString().split('T')[0];
    const goal = (data.goals || []).find((g: DailyGoal) => g.goalDate === today) || {
      id: Date.now(),
      goalOunces: 64,
      goalDate: today,
      createdAt: new Date().toISOString()
    };
    return of(goal);
  }

  public updateTodaysGoal(goalOunces: number): Observable<DailyGoal> {
    const data = this.getData();
    const today = new Date().toISOString().split('T')[0];
    
    if (!data.goals) {
      data.goals = [];
    }
    
    let todaysGoal = data.goals.find((g: DailyGoal) => g.goalDate === today);
    
    if (todaysGoal) {
      todaysGoal.goalOunces = goalOunces;
    } else {
      todaysGoal = {
        id: Date.now(),
        goalOunces,
        goalDate: today,
        createdAt: new Date().toISOString()
      };
      data.goals.push(todaysGoal);
    }
    
    this.saveData(data);
    return of(todaysGoal);
  }
}
