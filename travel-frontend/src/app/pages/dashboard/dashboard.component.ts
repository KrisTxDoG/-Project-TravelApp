import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TravelService } from '../../services/travel.service';

interface Itinerary {
  id: number;
  date: string;
  title: string;
  description: string;
  location: string;
}

interface TravelPlan {
  id: number;
  userId: number;
  startDate: string;
  endDate: string;
  destination: string;
  totalDays: number;
  itineraries: Itinerary[];
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  travelPlans: TravelPlan[] = [];
  loading: boolean = true;
  error: string = '';
  showNewPlanForm: boolean = false;
  showEditItinerary: boolean = false;

  newPlan = {
    destination: '',
    startDate: '',
    endDate: ''
  };

  newItinerary = {
    date: '',
    title: '',
    description: '',
    location: ''
  };

  selectedPlan: TravelPlan | null = null;
  selectedItinerary: Itinerary | null = null;

  constructor(private travelService: TravelService) {}

  ngOnInit() {
    this.loadTravelPlans();
  }

  loadTravelPlans() {
    this.travelService.getTravelPlans().subscribe({
      next: (data) => {
        this.travelPlans = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Failed to load travel plans';
        this.loading = false;
      }
    });
  }

  createTravelPlan() {
    if (!this.newPlan.destination || !this.newPlan.startDate || !this.newPlan.endDate) {
      this.error = 'Please fill in all fields';
      return;
    }

    this.travelService.createTravelPlan(this.newPlan).subscribe({
      next: (plan) => {
        this.travelPlans.push(plan);
        this.newPlan = { destination: '', startDate: '', endDate: '' };
        this.showNewPlanForm = false;
      },
      error: (error) => {
        this.error = 'Failed to create travel plan';
      }
    });
  }

  addItinerary(plan: TravelPlan) {
    this.selectedPlan = plan;
    this.showEditItinerary = true;
  }

  saveItinerary() {
    if (!this.selectedPlan || !this.newItinerary.date || !this.newItinerary.title) {
      this.error = 'Please fill in all fields';
      return;
    }

    this.travelService.addItinerary(this.selectedPlan.id, this.newItinerary).subscribe({
      next: (itinerary) => {
        this.selectedPlan!.itineraries.push(itinerary);
        this.newItinerary = { date: '', title: '', description: '', location: '' };
        this.showEditItinerary = false;
      },
      error: (error) => {
        this.error = 'Failed to add itinerary';
      }
    });
  }

  editItinerary(itinerary: Itinerary) {
    this.selectedItinerary = itinerary;
    this.newItinerary = { ...itinerary };
    this.showEditItinerary = true;
  }

  deleteItinerary(planId: number, itineraryId: number) {
    if (confirm('Are you sure you want to delete this itinerary?')) {
      this.travelService.deleteItinerary(planId, itineraryId).subscribe({
        next: () => {
          const plan = this.travelPlans.find(p => p.id === planId);
          if (plan) {
            plan.itineraries = plan.itineraries.filter(i => i.id !== itineraryId);
          }
        },
        error: (error) => {
          this.error = 'Failed to delete itinerary';
        }
      });
    }
  }

  deleteTravelPlan(planId: number) {
    if (confirm('Are you sure you want to delete this travel plan?')) {
      this.travelService.deleteTravelPlan(planId).subscribe({
        next: () => {
          this.travelPlans = this.travelPlans.filter(p => p.id !== planId);
        },
        error: (error) => {
          this.error = 'Failed to delete travel plan';
        }
      });
    }
  }

  getTotalDays(startDate: string, endDate: string): number {
    const start = new Date(startDate);
    const end = new Date(endDate);
    const diffTime = Math.abs(end.getTime() - start.getTime());
    return Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;
  }

  getPlannedDays(): number {
    return this.travelPlans.reduce((sum, plan) => sum + this.getTotalDays(plan.startDate, plan.endDate), 0);
  }

  getItineraryCount(): number {
    return this.travelPlans.reduce((sum, plan) => sum + plan.itineraries.length, 0);
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    window.location.href = '/login';
  }
}
