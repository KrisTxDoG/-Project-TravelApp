package com.travelapp.service;

import com.travelapp.entity.Itinerary;
import com.travelapp.entity.TravelPlan;
import com.travelapp.entity.User;
import com.travelapp.repository.ItineraryRepository;
import com.travelapp.repository.TravelPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TravelService {
    @Autowired
    private TravelPlanRepository travelPlanRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    public TravelPlan createTravelPlan(User user, String destination, String startDate, String endDate) {
        int totalDays = calculateTotalDays(startDate, endDate);
        TravelPlan travelPlan = new TravelPlan();
        travelPlan.setUser(user);
        travelPlan.setDestination(destination);
        travelPlan.setStartDate(startDate);
        travelPlan.setEndDate(endDate);
        travelPlan.setTotalDays(totalDays);
        return travelPlanRepository.save(travelPlan);
    }

    public List<TravelPlan> getUserTravelPlans(User user) {
        return travelPlanRepository.findByUser(user);
    }

    public Optional<TravelPlan> getTravelPlanById(Long id) {
        return travelPlanRepository.findById(id);
    }

    public void deleteTravelPlan(Long id) {
        travelPlanRepository.deleteById(id);
    }

    public Itinerary addItinerary(TravelPlan travelPlan, String date, String title, String description, String location) {
        Itinerary itinerary = new Itinerary();
        itinerary.setTravelPlan(travelPlan);
        itinerary.setDate(date);
        itinerary.setTitle(title);
        itinerary.setDescription(description);
        itinerary.setLocation(location);
        return itineraryRepository.save(itinerary);
    }

    public Optional<Itinerary> getItineraryById(Long id) {
        return itineraryRepository.findById(id);
    }

    public void deleteItinerary(Long id) {
        itineraryRepository.deleteById(id);
    }

    public Itinerary updateItinerary(Itinerary itinerary, String date, String title, String description, String location) {
        itinerary.setDate(date);
        itinerary.setTitle(title);
        itinerary.setDescription(description);
        itinerary.setLocation(location);
        return itineraryRepository.save(itinerary);
    }

    private int calculateTotalDays(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return (int) ChronoUnit.DAYS.between(start, end) + 1;
    }
}
