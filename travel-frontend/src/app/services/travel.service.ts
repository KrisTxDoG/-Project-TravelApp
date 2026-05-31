import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TravelService {
  private apiUrl = 'http://localhost:8080/api/travel';

  constructor(private http: HttpClient) {}

  getTravelPlans(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/plans`);
  }

  createTravelPlan(plan: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/plans`, plan);
  }

  getTravelPlan(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/plans/${id}`);
  }

  updateTravelPlan(id: number, plan: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/plans/${id}`, plan);
  }

  deleteTravelPlan(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/plans/${id}`);
  }

  addItinerary(planId: number, itinerary: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/plans/${planId}/itineraries`, itinerary);
  }

  updateItinerary(planId: number, itineraryId: number, itinerary: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/plans/${planId}/itineraries/${itineraryId}`, itinerary);
  }

  deleteItinerary(planId: number, itineraryId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/plans/${planId}/itineraries/${itineraryId}`);
  }
}
