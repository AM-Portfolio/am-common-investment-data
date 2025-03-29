package com.am.common.investment.persistence.repository.financial;

import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.common.investment.persistence.document.BaseDocument;

/**
 * Generic repository interface for financial documents with version and time sorting
 */
public interface BaseDocumentRepository<T extends BaseDocument, ID> extends MongoRepository<T, ID> {
    
    Optional<T> findBySymbol(String symbol, Sort sort);

    default Optional<T> findBySymbolWithVersionAndTime(String symbol) {
        return findBySymbol(symbol, Sort.by(Sort.Order.desc("docVersion"), Sort.Order.desc("audit.updatedAt")));
    }
}
