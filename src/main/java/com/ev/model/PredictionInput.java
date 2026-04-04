package com.ev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Input model for EV demand prediction
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionInput {
    private String county;
    private LocalDate currentDate;
    private double currentEvTotal;
    private double evTotalLag1;
    private double evTotalLag2;
    private double evTotalLag3;
}
