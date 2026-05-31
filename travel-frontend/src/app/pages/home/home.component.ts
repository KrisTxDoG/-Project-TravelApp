import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  features = [
    {
      icon: '📅',
      title: 'Track Travel Days',
      description: 'Easily count and manage your total travel days'
    },
    {
      icon: '📍',
      title: 'Plan Itineraries',
      description: 'Create detailed day-by-day itineraries for your trips'
    },
    {
      icon: '✈️',
      title: 'Manage Destinations',
      description: 'Organize multiple travel plans and destinations'
    },
    {
      icon: '📱',
      title: 'Mobile Responsive',
      description: 'Access your travel plans on any device'
    }
  ];
}
