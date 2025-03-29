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
public class GrowthMetrics {
    private double revenueGrowthPer;
    private double netProfitGrowth;
    private double netProfitMarginGrowth;
    private double netSalesGrowth;
    private double ebitdaGrowth;
    private double ebitGrowth;
    private double patGrowth;
    private double patMarginGrowth;
}
