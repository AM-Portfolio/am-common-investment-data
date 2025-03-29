package com.am.common.investment.service;

import com.am.common.investment.model.board.BoardOfDirectors;

import java.util.Optional;

/**
 * Service for managing board of directors data
 */
public interface BoardOfDirectorsService {
    
    /**
     * Get board of directors by company ID (symbol)
     * 
     * @param companyId The company ID or symbol
     * @return Optional containing the board of directors if found
     */
    Optional<BoardOfDirectors> getBoardOfDirectorsByCompanyId(String companyId);
    
    /**
     * Save or update board of directors information
     * 
     * @param boardOfDirectors The board of directors to save
     * @return The saved board of directors
     */
    BoardOfDirectors saveBoardOfDirectors(BoardOfDirectors boardOfDirectors);
}
