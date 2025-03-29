package com.am.common.investment.model.equity.financial.resultstatement;

import com.am.common.investment.model.equity.financial.BaseModel;
import com.am.common.investment.model.equity.metrics.CostMetrics;
import com.am.common.investment.model.equity.metrics.EpsMetrics;
import com.am.common.investment.model.equity.metrics.GrowthMetrics;
import com.am.common.investment.model.equity.metrics.ProfitMetrics;
import com.am.common.investment.model.equity.metrics.TaxMetrics;
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
public class FinancialResult extends BaseModel {
    private String yearEnd;
    
    // Revenue Metrics
    private double totalRevenue;
    private double operatingRevenue;
    private double otherIncome;
    
    // Cost Metrics
    private CostMetrics costMetrics;
    
    // Profit Metrics
    private ProfitMetrics profitMetrics;
    
    // Tax Metrics
    private TaxMetrics taxMetrics;
    
    // EPS Metrics
    private EpsMetrics epsMetrics;
    
    // Growth Metrics
    private GrowthMetrics growthMetrics;
    
    // Additional Metrics
    private double profitFromAssociates;
    private double patMargin;
    private double patGrowth;
    private double patMarginGrowth;
    private double adjEpsInRsBasic;
    private double adjEpsInRsDiluted;
}
