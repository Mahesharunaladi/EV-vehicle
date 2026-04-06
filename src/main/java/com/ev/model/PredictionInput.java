package com.ev.model;

import java.time.LocalDate;

/**
 * Input model for EV demand prediction
 */
public class PredictionInput {
    private String county;
    private LocalDate currentDate;
    private double currentEvTotal;
    private double evTotalLag1;
    private double evTotalLag2;
    private double evTotalLag3;

    public PredictionInput() {
    }

    public PredictionInput(String county, LocalDate currentDate, double currentEvTotal,
                          double evTotalLag1, double evTotalLag2, double evTotalLag3) {
        this.county = county;
        this.currentDate = currentDate;
        this.currentEvTotal = currentEvTotal;
        this.evTotalLag1 = evTotalLag1;
        this.evTotalLag2 = evTotalLag2;
        this.evTotalLag3 = evTotalLag3;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }

    public double getCurrentEvTotal() {
        return currentEvTotal;
    }

    public void setCurrentEvTotal(double currentEvTotal) {
        this.currentEvTotal = currentEvTotal;
    }

    public double getEvTotalLag1() {
        return evTotalLag1;
    }

    public void setEvTotalLag1(double evTotalLag1) {
        this.evTotalLag1 = evTotalLag1;
    }

    public double getEvTotalLag2() {
        return evTotalLag2;
    }

    public void setEvTotalLag2(double evTotalLag2) {
        this.evTotalLag2 = evTotalLag2;
    }

    public double getEvTotalLag3() {
        return evTotalLag3;
    }

    public void setEvTotalLag3(double evTotalLag3) {
        this.evTotalLag3 = evTotalLag3;
    }
}
