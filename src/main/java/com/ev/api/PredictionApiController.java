package com.ev.api;

import com.ev.model.PredictionInput;
import com.ev.model.PredictionResult;
import com.ev.service.LocationService;
import com.ev.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API controller for EV demand prediction
 */
@RestController
@RequestMapping("/api")
public class PredictionApiController {
    @Autowired
    private PredictionService predictionService;

    @Autowired
    private LocationService locationService;

    /**
     * Get all available locations
     */
    @GetMapping("/locations")
    public ResponseEntity<List<String>> getLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    /**
     * Get location encoding
     */
    @GetMapping("/locations/{location}/encoding")
    public ResponseEntity<Map<String, Object>> getLocationEncoding(@PathVariable String location) {
        int encoding = locationService.getLocationEncoding(location);
        return ResponseEntity.ok(Map.of(
                "location", location,
                "encoding", encoding
        ));
    }

    /**
     * Make prediction API endpoint
     */
    @PostMapping("/predict")
    public ResponseEntity<PredictionResult> predict(@RequestBody PredictionInput input) {
        PredictionResult result = predictionService.predictDemand(input);
        return ResponseEntity.ok(result);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "EV Demand Prediction Service is running"
        ));
    }
}
