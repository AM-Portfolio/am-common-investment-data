package com.am.common.investment.service.mapper;

import com.am.common.investment.model.board.BoardOfDirectors;
import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mapper for converting between BoardOfDirectors model and BoardOfDirectorsDocument
 */
@Component
public class BoardOfDirectorsMapper {
    /**
     * Convert from document to model
     */
    public BoardOfDirectors toModel(BoardOfDirectorsDocument document) {
        if (document == null) {
            return null;
        }
        
        return BoardOfDirectors.builder()
                .companyId(document.getCompanyId())
                .companyName(document.getCompanyName())
                .directors(document.getDirectors())
                .lastUpdated(document.getDataAsOf())
                .build();
    }
    
    /**
     * Convert from model to document
     */
    public BoardOfDirectorsDocument toDocument(BoardOfDirectors model) {
        if (model == null) {
            return null;
        }
        
        return BoardOfDirectorsDocument.builder()
                .companyId(model.getCompanyId())
                .companyName(model.getCompanyName())
                .directors(model.getDirectors())
                .dataAsOf(model.getLastUpdated())
                .lastUpdated(LocalDateTime.now())
                .build();
    }
}
