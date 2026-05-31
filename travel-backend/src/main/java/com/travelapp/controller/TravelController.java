package com.travelapp.controller;

import com.travelapp.entity.Itinerary;
import com.travelapp.entity.TravelPlan;
import com.travelapp.entity.User;
import com.travelapp.service.JwtService;
import com.travelapp.service.TravelService;
import com.travelapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/travel")
public class TravelController {
    @Autowired
    private TravelService travelService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private User getCurrentUser(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Long userId = jwtService.extractUserId(token);
        Optional<User> user = userService.findById(userId);
        return user.orElse(null);
    }

    @GetMapping("/plans")
    public ResponseEntity<?> getTravelPlans(@RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        List<TravelPlan> plans = travelService.getUserTravelPlans(user);
        return ResponseEntity.ok(plans);
    }

    @PostMapping("/plans")
    public ResponseEntity<?> createTravelPlan(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String destination = request.get("destination");
        String startDate = request.get("startDate");
        String endDate = request.get("endDate");

        TravelPlan plan = travelService.createTravelPlan(user, destination, startDate, endDate);
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/plans/{id}")
    public ResponseEntity<?> getTravelPlan(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<TravelPlan> plan = travelService.getTravelPlanById(id);
        if (plan.isEmpty() || !plan.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body("Travel plan not found");
        }
        return ResponseEntity.ok(plan.get());
    }

    @DeleteMapping("/plans/{id}")
    public ResponseEntity<?> deleteTravelPlan(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<TravelPlan> plan = travelService.getTravelPlanById(id);
        if (plan.isEmpty() || !plan.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body("Travel plan not found");
        }

        travelService.deleteTravelPlan(id);
        return ResponseEntity.ok("Travel plan deleted");
    }

    @PostMapping("/plans/{planId}/itineraries")
    public ResponseEntity<?> addItinerary(@PathVariable Long planId, @RequestHeader("Authorization") String token, @RequestBody Map<String, String> request) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<TravelPlan> plan = travelService.getTravelPlanById(planId);
        if (plan.isEmpty() || !plan.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body("Travel plan not found");
        }

        String date = request.get("date");
        String title = request.get("title");
        String description = request.get("description");
        String location = request.get("location");

        Itinerary itinerary = travelService.addItinerary(plan.get(), date, title, description, location);
        return ResponseEntity.ok(itinerary);
    }

    @DeleteMapping("/plans/{planId}/itineraries/{itineraryId}")
    public ResponseEntity<?> deleteItinerary(@PathVariable Long planId, @PathVariable Long itineraryId, @RequestHeader("Authorization") String token) {
        User user = getCurrentUser(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Optional<TravelPlan> plan = travelService.getTravelPlanById(planId);
        if (plan.isEmpty() || !plan.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(404).body("Travel plan not found");
        }

        Optional<Itinerary> itinerary = travelService.getItineraryById(itineraryId);
        if (itinerary.isEmpty()) {
            return ResponseEntity.status(404).body("Itinerary not found");
        }

        travelService.deleteItinerary(itineraryId);
        return ResponseEntity.ok("Itinerary deleted");
    }
}
