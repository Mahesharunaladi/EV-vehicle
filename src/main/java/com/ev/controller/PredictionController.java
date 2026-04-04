package com.ev.controller;

import com.ev.model.PredictionInput;
import com.ev.model.PredictionResult;
import com.ev.service.LocationService;
import com.ev.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Web controller for EV demand prediction
 */
@Controller
@RequiredArgsConstructor
public class PredictionController {
    private final PredictionService predictionService;
    private final LocationService locationService;

    /**
     * Display main prediction page
     */
    @GetMapping("/")
    public String index(Model model) {
        List<String> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("predictionInput", new PredictionInput());
        return "index";
    }

    /**
     * Handle prediction form submission
     */
    @PostMapping("/predict")
    public String predict(@ModelAttribute PredictionInput input, Model model) {
        PredictionResult result = predictionService.predictDemand(input);
        
        model.addAttribute("result", result);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("predictionInput", input);
        model.addAttribute("currentDate", LocalDate.now());
        
        return "index";
    }
}
