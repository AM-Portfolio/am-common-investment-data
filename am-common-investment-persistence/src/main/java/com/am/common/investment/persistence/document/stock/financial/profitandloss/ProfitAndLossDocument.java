package com.am.common.investment.persistence.document.stock.financial.profitandloss;

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
@Document(collection = "profit_and_loss")
public class ProfitAndLossDocument extends BaseDocument{
    
    private Double totalRevenue;
    private Double operatingRevenue;
    
    private CostMetrics costMetrics;
    
    private ProfitMetrics profitMetrics;
    
    private GrowthMetrics growthMetrics;
    
    private EpsMetrics epsMetrics;
    
    private TaxMetrics taxMetrics;
}
