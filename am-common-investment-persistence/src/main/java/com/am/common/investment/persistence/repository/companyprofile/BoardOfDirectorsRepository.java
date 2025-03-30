package com.am.common.investment.persistence.repository.companyprofile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;

import java.util.Optional;

/**
 * Repository for accessing BoardOfDirectorsDocument in MongoDB
 */
@Repository
public interface BoardOfDirectorsRepository extends MongoRepository<BoardOfDirectorsDocument, String> {
    
    /**
     * Find a board of directors document by company ID (symbol)
     * 
     * @param companyId The company ID or symbol
     * @return Optional containing the document if found
     */
    Optional<BoardOfDirectorsDocument> findByCompanyId(String companyId);
    
    /**
     * Delete a board of directors document by company ID (symbol)
     * 
     * @param companyId The company ID or symbol
     */
    void deleteByCompanyId(String companyId);
}
