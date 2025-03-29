package com.am.common.investment.persistence.document.stock.financial.cashflow;

import org.springframework.data.mongodb.core.mapping.Document;

import com.am.common.investment.model.equity.metrics.CashFlowFinancingMetrics;
import com.am.common.investment.model.equity.metrics.CashFlowInvestingMetrics;
import com.am.common.investment.model.equity.metrics.CashFlowOperatingMetrics;
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
@Document(collection = "cash_flow")
public class CashFlowDocument extends BaseDocument{
    
    // Operating Activities
    private CashFlowOperatingMetrics operatingMetrics;
    
    // Investing Activities
    private CashFlowInvestingMetrics investingMetrics;
    
    // Financing Activities
    private CashFlowFinancingMetrics financingMetrics;
    
    // Summary Metrics
    private Double netCashFlow;
    private Double freeCashFlow;
}
