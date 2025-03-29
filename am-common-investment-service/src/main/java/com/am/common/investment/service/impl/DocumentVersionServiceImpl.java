package com.am.common.investment.service.impl;

import org.springframework.stereotype.Service;

import com.am.common.investment.persistence.document.BaseDocument;
import com.am.common.investment.service.DocumentVersionService;

/**
 * Implementation of document version management service
 */
@Service
public class DocumentVersionServiceImpl implements DocumentVersionService {
    
    private static final String INITIAL_VERSION = "1.0.0";
    
    @Override
    public String getNextVersion(String symbol) {
        // For now, we'll just return the next version based on initial version
        // In a more complex implementation, we could query the database for the latest version
        return incrementVersion(INITIAL_VERSION);
    }
    
    @Override
    public BaseDocument incrementVersion(BaseDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        
        String currentVersion = document.getDocVersion();
        if (currentVersion == null) {
            currentVersion = INITIAL_VERSION;
        }
        
        document.setDocVersion(incrementVersion(currentVersion));
        return document;
    }
    
    @Override
    public String getInitialVersion() {
        return INITIAL_VERSION;
    }
    
    /**
     * Increment a version string in the format x.y.z
     * 
     * @param version The version string to increment
     * @return The incremented version string
     */
    private String incrementVersion(String version) {
        if (version == null || version.isEmpty()) {
            return INITIAL_VERSION;
        }
        
        String[] parts = version.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }
        
        try {
            int major = Integer.parseInt(parts[0]);
            int minor = Integer.parseInt(parts[1]);
            int patch = Integer.parseInt(parts[2]);
            
            // Increment patch version
            patch++;
            
            return String.format("%d.%d.%d", major, minor, patch);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid version format: " + version, e);
        }
    }
}
