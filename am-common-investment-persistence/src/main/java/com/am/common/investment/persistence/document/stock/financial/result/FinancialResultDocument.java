package com.am.common.investment.persistence.document.stock.financial.result;

import org.springframework.data.mongodb.core.mapping.Document;

import com.am.common.investment.model.equity.metrics.CostMetrics;
import com.am.common.investment.model.equity.metrics.EpsMetrics;
import com.am.common.investment.model.equity.metrics.GrowthMetrics;
import com.am.common.investment.model.equity.metrics.ProfitMetrics;
import com.am.common.investment.model.equity.metrics.TaxMetrics;
import com.am.common.investment.persistence.document.BaseDocument;
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
@Document(collection = "financial_result")
public class FinancialResultDocument extends BaseDocument {
   private String yearEnd;
    
    // Revenue Metrics
    private Double totalRevenue;
    private Double operatingRevenue;
    private Double otherIncome;
    
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
    private Double profitFromAssociates;
    private Double patMargin;
    private Double patGrowth;
    private Double patMarginGrowth;
    private Double adjEpsInRsBasic;
    private Double adjEpsInRsDiluted; 
}
