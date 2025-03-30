package com.am.common.investment.persistence.repository.stock.financial;

import java.util.Optional;
import java.util.UUID;

import com.am.common.investment.persistence.document.stock.financial.result.FinancialResultDocument;
import com.am.common.investment.persistence.repository.financial.BaseDocumentRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialResultRepository extends MongoRepository<FinancialResultDocument, UUID> {
    Optional<FinancialResultDocument> findBySymbol(String symbol, Sort sort);

    default Optional<FinancialResultDocument> findBySymbolWithVersionAndTime(String symbol) {
        return findBySymbol(symbol, Sort.by(Sort.Order.desc("docVersion"), Sort.Order.desc("audit.updatedAt")));
    }
}
