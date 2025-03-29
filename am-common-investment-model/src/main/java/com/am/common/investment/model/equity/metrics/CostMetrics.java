package com.am.common.investment.model.equity.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CostMetrics {
    private double totalExpenditure;
    private double rawMaterialCost;
    private double manufacturingCost;
    private double employeeCost;
    private double interest;
    private double otherCost;
    private double operatingExpenses;
    private double depreciationAndAmortization;
}
