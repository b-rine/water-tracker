import { Component } from '@angular/core';
import { WaterLogComponent } from './water-log/water-log.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css',
  imports: [WaterLogComponent]
})
export class AppComponent {
  title = 'frontend';
}
