package com.am.common.investment.service;

import com.am.common.investment.persistence.document.BaseDocument;

/**
 * Service interface for managing document versions
 */
public interface DocumentVersionService {
    
    /**
     * Get the next version number for a document
     * 
     * @param symbol The document symbol
     * @return The next version number
     */
    String getNextVersion(String symbol);
    
    /**
     * Increment the version of a document
     * 
     * @param document The document to increment version for
     * @return The updated document with incremented version
     */
    BaseDocument incrementVersion(BaseDocument document);
    
    /**
     * Get the initial version for a new document
     * 
     * @return The initial version number
     */
    String getInitialVersion();
}
