package com.am.common.investment.model.equity;

import java.math.BigDecimal;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquityFundamental {
    private String symbol;
    private String isin;
    private Instant time;
    
    // Valuation Metrics
    private Double pe;
    private Double pb;
    private Double ps;
    private Double pcf;
    private BigDecimal marketCap;
    
    // Financial Ratios
    private Double currentRatio;
    private Double quickRatio;
    private Double debtToEquity;
    private Double roa;
    private Double roe;
    
    // Growth Metrics
    private Double revenueGrowth;
    private Double profitGrowth;
    private Double epsgrowth;
    
    // Dividend Metrics
    private Double dividendYield;
    private Double payoutRatio;
}
