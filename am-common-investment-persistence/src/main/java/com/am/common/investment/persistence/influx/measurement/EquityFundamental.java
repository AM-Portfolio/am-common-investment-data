package com.am.common.investment.persistence.influx.measurement;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "equity_fundamentals")
public class EquityFundamental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String isin;

    @Column(nullable = false)
    private Instant time;
    
    @Embedded
    private ValuationMetrics valuationMetrics;
    
    @Embedded
    private FinancialRatios financialRatios;
    
    @Embedded
    private GrowthMetrics growthMetrics;
    
    @Embedded
    private DividendMetrics dividendMetrics;
}

@Data
@Embeddable
class ValuationMetrics {
    private Double pe;
    private Double pb;
    private Double ps;
    private Double pcf;
    private BigDecimal marketCap;
}

@Data
@Embeddable
class FinancialRatios {
    private Double currentRatio;
    private Double quickRatio;
    private Double debtToEquity;
    private Double roa;
    private Double roe;
}

@Data
@Embeddable
class GrowthMetrics {
    private Double revenueGrowth;
    private Double profitGrowth;
    private Double epsgrowth;
}

@Data
@Embeddable
class DividendMetrics {
    private Double dividendYield;
    private Double payoutRatio;
}
