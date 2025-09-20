import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { WaterService } from './app/water-log/services/water-log.service';
import { createWaterService } from './app/services/water-service.factory';

bootstrapApplication(AppComponent, {
    providers: [
        importProvidersFrom(HttpClientModule),
        {
            provide: WaterService,
            useFactory: createWaterService,
            deps: [HttpClient]
        }
    ]
}).catch((err) => console.error(err));
