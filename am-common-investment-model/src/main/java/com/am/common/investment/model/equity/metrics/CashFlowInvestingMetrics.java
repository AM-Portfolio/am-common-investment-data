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
public class CashFlowInvestingMetrics {
    private double cashFromInvestingActivities;
    
    // Fixed Assets
    private double fixedAssetsPurchased;
    private double fixedAssetsSold;
    
    // Investments
    private double investmentsPurchased;
    private double investmentsSold;
    private double otherInvestingItems;
    
    // Other Investments
    private double acquisitionOfCompanies;
    private double interCorporateDeposits;
    private double investmentInGroupCos;
    private double investmentInSubsidiaries;
}
