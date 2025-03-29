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
public class AssetsMetrics {
    private double inventory;
    private double fixedAssets;
    private double capitalWorkInProgress;
    private double intangibleAssets;
    private double intangibleAssetsUnderDev;
    private double netBlock;
    
    // Current Assets
    private double currentAssets;
    private double accountsReceivables;
    private double shortTermInvestments;
    private double cashAndBankBalances;
    
    // Non-Current Assets
    private double nonCurrentAssets;
    private double longTermInvestments;
}
