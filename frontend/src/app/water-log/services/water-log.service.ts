import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WaterLog} from '../models/water-log.model';
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

  public addWaterLog(waterLog: WaterLog): Observable<WaterLog> {
    return this.http.post<WaterLog>(`${this.apiServerUrl}/tracker`, waterLog);
  }

  public deleteWaterLog(logId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/tracker/${logId}`);
  }

}
