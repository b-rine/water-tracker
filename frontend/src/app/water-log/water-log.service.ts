import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {WaterLog} from './water-log.interface';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WaterService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getWater(): Observable<WaterLog[]> {
    return this.http.get<WaterLog[]>(`${this.apiServerUrl}/tracker`);
  }

  public addWater(waterLog: WaterLog): Observable<WaterLog> {
    return this.http.post<WaterLog>(`${this.apiServerUrl}/tracker`, waterLog);
  }

}
