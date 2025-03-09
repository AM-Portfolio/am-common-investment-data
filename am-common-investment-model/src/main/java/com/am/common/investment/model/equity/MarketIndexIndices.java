package com.am.common.investment.model.equity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketIndexIndices {
    private String key;
    private String index;
    private String indexSymbol;
    private MarketData marketData;
    private FundamentalRatios fundamentalRatios;
    private MarketBreadth marketBreadth;
    private HistoricalComparison historicalComparison;
    private ChartPaths chartPaths;
    private LocalDateTime timestamp;
}
