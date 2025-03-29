package com.am.common.investment.persistence.repository.financial;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.common.investment.persistence.document.BaseDocument;

/**
 * Generic repository interface for financial documents with version and time sorting
 */
public interface BaseDocumentRepository<T extends BaseDocument, ID> extends MongoRepository<T, ID> {
    
    /**
     * Find a document by symbol, sorted by version and update time
     * 
     * @param symbol The symbol to search for
     * @return Optional containing the latest document if found
     */
    Optional<T> findBySymbolWithSort(String symbol, Sort sort);

    /**
     * Find a document by symbol, using default version and time sorting
     * 
     * @param symbol The symbol to search for
     * @return Optional containing the latest document if found
     */
    default Optional<T> findBySymbolWithVersionAndTime(String symbol) {
        return findBySymbolWithSort(symbol, Sort.by(Sort.Order.desc("docVersion"), Sort.Order.desc("audit.updatedAt")));
    }
}
