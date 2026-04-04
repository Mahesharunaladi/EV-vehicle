package com.ev.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Output model for EV demand prediction results
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PredictionResult {
    @JsonProperty("prediction")
    private double prediction;

    @JsonProperty("features")
    private Map<String, Double> features;

    @JsonProperty("error")
    private String error;

    @JsonProperty("success")
    private boolean success;
}
