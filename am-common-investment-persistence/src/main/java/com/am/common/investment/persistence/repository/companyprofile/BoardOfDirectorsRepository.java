package com.am.common.investment.persistence.repository.companyprofile;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;
import com.am.common.investment.persistence.repository.financial.BaseDocumentRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for accessing BoardOfDirectorsDocument in MongoDB
 */
@Repository
public interface BoardOfDirectorsRepository extends MongoRepository<BoardOfDirectorsDocument, UUID> {
    Optional<BoardOfDirectorsDocument> findBySymbol(String symbol, Sort sort);

    default Optional<BoardOfDirectorsDocument> findBySymbolWithVersionAndTime(String symbol) {
        return findBySymbol(symbol, Sort.by(Sort.Order.desc("docVersion"), Sort.Order.desc("audit.updatedAt")));
    }
}
