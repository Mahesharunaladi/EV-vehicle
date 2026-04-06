package com.ev.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/**
 * Output model for EV demand prediction results
 */
public class PredictionResult {
    @JsonProperty("prediction")
    private double prediction;

    @JsonProperty("features")
    private Map<String, Double> features;

    @JsonProperty("error")
    private String error;

    @JsonProperty("success")
    private boolean success;

    public PredictionResult() {
    }

    public PredictionResult(double prediction, Map<String, Double> features, String error, boolean success) {
        this.prediction = prediction;
        this.features = features;
        this.error = error;
        this.success = success;
    }

    public static Builder builder() {
        return new Builder();
    }

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

    public static class Builder {
        private double prediction;
        private Map<String, Double> features;
        private String error;
        private boolean success;

        public Builder prediction(double prediction) {
            this.prediction = prediction;
            return this;
        }

        public Builder features(Map<String, Double> features) {
            this.features = features;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public PredictionResult build() {
            return new PredictionResult(prediction, features, error, success);
        }
    }
}
