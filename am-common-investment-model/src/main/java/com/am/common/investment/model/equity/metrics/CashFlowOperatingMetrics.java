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
public class CashFlowOperatingMetrics {
    private double cashFromOperatingActivities;
    private double profitFromOperations;
    private double interestReceived;
    private double dividendReceived;
    private double directTaxes;
    private double exceptionalCfItems;
    
    // Working Capital Changes
    private double receivables;
    private double inventory;
    private double payables;
    private double workingCapitalChanges;
    private double otherWcItems;
}
