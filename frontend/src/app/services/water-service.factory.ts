import { HttpClient } from '@angular/common/http';
import { WaterService } from '../water-log/services/water-log.service';
import { LocalStorageWaterService } from './local-storage-water.service';
import { environment } from '../../environments/environment';

export function createWaterService(http: HttpClient): any {
  if (environment.useLocalStorage) {
    return new LocalStorageWaterService();
  }
  return new WaterService(http);
}
