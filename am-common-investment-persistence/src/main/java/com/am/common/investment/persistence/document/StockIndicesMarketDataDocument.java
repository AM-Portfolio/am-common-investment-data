package com.am.common.investment.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.am.common.investment.model.stockindice.AuditData;
import com.am.common.investment.model.stockindice.Metadata;
import com.am.common.investment.model.stockindice.StockData;

import java.util.List;
import java.util.UUID;

/**
 * MongoDB document for stock indices market data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stock_indices_market_data")
public class StockIndicesMarketDataDocument {
    
    @Id
    private UUID id;
    private String indexSymbol;
    private List<StockData> data;
    private Metadata metadata;
    private AuditData audit;
    private String docVersion;
}
