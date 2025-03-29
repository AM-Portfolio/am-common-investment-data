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
public class CashFlowFinancingMetrics {
    private double cashFromFinancialActivities;
    private double netCashFlow;
    
    // Borrowings
    private double proceedsFromBorrowings;
    private double repaymentBorrowings;
    private double interestPaidFin;
    
    // Share Capital
    private double proceedsFromShares;
    private double redemptionAndCancellationOfShares;
    
    // Financial Liabilities
    private double financialLiabilities;
    private double otherFinancingItems;
    
    // Dividends
    private double dividendPaid;
    private double dividendReceived;
}
