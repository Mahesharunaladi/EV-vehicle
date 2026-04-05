package com.ev.service;

import com.ev.model.PredictionInput;
import com.ev.model.PredictionResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * Service for EV demand prediction
 */
@Service
@RequiredArgsConstructor
public class PredictionService {
    private static final Logger logger = LoggerFactory.getLogger(PredictionService.class);
    private static final String[] FEATURE_NAMES = {
            "months_since_start",
            "county_encoded",
            "ev_total_lag1",
            "ev_total_lag2",
            "ev_total_lag3",
            "ev_total_roll_mean_3",
            "ev_total_pct_change_1",
            "ev_total_pct_change_3",
            "ev_growth_slope"
    };

    private final LocationService locationService;
    private final ModelService modelService;

    /**
     * Predict EV demand for next month
     */
    public PredictionResult predictDemand(PredictionInput input) {
        try {
            // Calculate features
            Map<String, Double> features = calculateFeatures(input);

            // Create feature vector
            double[] featureVector = extractFeatureVector(features);

            // Make prediction using the model
            double prediction = modelService.predict(featureVector);

            return PredictionResult.builder()
                    .prediction(prediction)
                    .features(features)
                    .success(true)
                    .build();

        } catch (Exception e) {
            logger.error("Error making prediction", e);
            return PredictionResult.builder()
                    .error(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    /**
     * Calculate all features for prediction
     */
    private Map<String, Double> calculateFeatures(PredictionInput input) {
        Map<String, Double> features = new LinkedHashMap<>();

        // Months since start (base date: May 2018)
        LocalDate baseDate = LocalDate.of(2018, 5, 1);
        LocalDate currentDate = input.getCurrentDate();
        long monthsSinceStart = calculateMonthsBetween(baseDate, currentDate);
        features.put("months_since_start", (double) monthsSinceStart);

        // County encoded
        int countyEncoded = locationService.getLocationEncoding(input.getCounty());
        features.put("county_encoded", (double) countyEncoded);

        // EV lags
        features.put("ev_total_lag1", input.getEvTotalLag1());
        features.put("ev_total_lag2", input.getEvTotalLag2());
        features.put("ev_total_lag3", input.getEvTotalLag3());

        // Rolling mean (3-month)
        double[] evTotals = {input.getEvTotalLag2(), input.getEvTotalLag1(), input.getCurrentEvTotal()};
        double rollMean = calculateMean(evTotals);
        features.put("ev_total_roll_mean_3", rollMean);

        // Percentage change
        double pctChange1 = input.getEvTotalLag1() > 0 ?
                (input.getCurrentEvTotal() - input.getEvTotalLag1()) / input.getEvTotalLag1() : 0;
        double pctChange3 = input.getEvTotalLag2() > 0 ?
                (input.getCurrentEvTotal() - input.getEvTotalLag2()) / input.getEvTotalLag2() : 0;

        features.put("ev_total_pct_change_1", pctChange1);
        features.put("ev_total_pct_change_3", pctChange3);

        // Growth slope
        double growthSlope = calculateGrowthSlope(evTotals);
        features.put("ev_growth_slope", growthSlope);

        return features;
    }

    /**
     * Calculate months between two dates
     */
    private long calculateMonthsBetween(LocalDate start, LocalDate end) {
        YearMonth startMonth = YearMonth.from(start);
        YearMonth endMonth = YearMonth.from(end);
        return startMonth.until(endMonth, java.time.temporal.ChronoUnit.MONTHS);
    }

    /**
     * Calculate mean of values
     */
    private double calculateMean(double[] values) {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    /**
     * Calculate growth slope using linear regression
     */
    private double calculateGrowthSlope(double[] evTotals) {
        if (evTotals.length < 2) {
            return 0.0;
        }

        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < evTotals.length; i++) {
            regression.addData(i, evTotals[i]);
        }

        return regression.getSlope();
    }

    /**
     * Extract feature vector from features map
     */
    private double[] extractFeatureVector(Map<String, Double> features) {
        double[] vector = new double[FEATURE_NAMES.length];
        for (int i = 0; i < FEATURE_NAMES.length; i++) {
            vector[i] = features.getOrDefault(FEATURE_NAMES[i], 0.0);
        }
        return vector;
    }
}
