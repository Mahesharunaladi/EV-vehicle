package com.ev.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


public class PredictionResult {
    @JsonProperty("prediction")
    private double prediction;

    @JsonProperty("features")
    private Map<String, Double> features;

    @JsonProperty("error")
    private String error;

    @JsonProperty("success")
    private boolean success;

    // Constructors
    public PredictionResult() {}

    public PredictionResult(double prediction, Map<String, Double> features, String error, boolean success) {
        this.prediction = prediction;
        this.features = features;
        this.error = error;
        this.success = success;
    }

    // Getters and Setters
    public double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public Map<String, Double> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, Double> features) {
        this.features = features;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Builder pattern
    public static PredictionResultBuilder builder() {
        return new PredictionResultBuilder();
    }

    public static class PredictionResultBuilder {
        private double prediction;
        private Map<String, Double> features;
        private String error;
        private boolean success;

        public PredictionResultBuilder prediction(double prediction) {
            this.prediction = prediction;
            return this;
        }

        public PredictionResultBuilder features(Map<String, Double> features) {
            this.features = features;
            return this;
        }

        public PredictionResultBuilder error(String error) {
            this.error = error;
            return this;
        }

        public PredictionResultBuilder success(boolean success) {
            this.success = success;
            return this;
        }

        public PredictionResult build() {
            return new PredictionResult(prediction, features, error, success);
        }
    }
}
