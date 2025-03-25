package com.am.common.investment.persistence.repository;

import com.am.common.investment.persistence.document.StockIndicesMarketDataDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * MongoDB repository for stock indices market data
 */
@Repository
public interface StockIndicesMarketDataRepository extends MongoRepository<StockIndicesMarketDataDocument, UUID> {
    
    /**
     * Find stock indices market data by multiple index symbols with sorting
     * 
     * @param indexSymbols Set of index symbols to search for
     * @return Page of matching documents sorted by updatedAt in descending order
     */
    Page<StockIndicesMarketDataDocument> findByIndexSymbolInOrderByAuditUpdatedAtDesc(Set<String> indexSymbols, Pageable pageable);
}
