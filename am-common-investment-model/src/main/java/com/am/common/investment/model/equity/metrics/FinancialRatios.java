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
public class FinancialRatios {
    // Market Ratios
    private double pe;
    private double evEbitda;
    private double priceBookValue;
    private double priceToCashflow;
    private double priceToFreeCashflow;
    private double priceSalesRatio;
    
    // Liquidity Ratios
    private double currentRatio;
    private double quickRatio;
    
    // Efficiency Ratios
    private double receivableDays;
    private double inventoryDays;
    private double payableDays;
    private double workingCapitalDays;
    private double inventoryTurnoverRatio;
    private double assetTurnoverRatio;
    private double cashConversionCycle;
    
    // Profitability Ratios
    private double pbditMargin;
    private double pbtMargin;
    private double ebitMargin;
    private double netProfitMargin;
    private double contributionProfitMargin;
    private double pbitMargin;
    
    // Return Ratios
    private double returnOnEquity;
    private double returnOnAssets;
    private double returnOnCapEmployed;
    
    // Leverage Ratios
    private double interestCoverageRatio;
    private double debtToEquityRatio;
    private double totalDebtToMarketCap;
    private double fixedCapitalToSalesRatio;
    
    // Growth Ratios
    private double marketCapToSales;
    private double pegRatio;
}
