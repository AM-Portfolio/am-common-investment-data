package com.am.common.investment.service.impl;

import com.am.common.investment.model.board.BoardOfDirectors;
import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;
import com.am.common.investment.persistence.repository.companyprofile.BoardOfDirectorsRepository;
import com.am.common.investment.service.BoardOfDirectorsService;
import com.am.common.investment.service.mapper.StockFinancialPerformanceMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the BoardOfDirectorsService
 */
@Service
public class BoardOfDirectorsServiceImpl implements BoardOfDirectorsService {
    
    private static final Logger logger = LoggerFactory.getLogger(BoardOfDirectorsServiceImpl.class);
    
    private final BoardOfDirectorsRepository repository;
    private final StockFinancialPerformanceMapper mapper;
    
    public BoardOfDirectorsServiceImpl(BoardOfDirectorsRepository repository, StockFinancialPerformanceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    @Override
    public Optional<BoardOfDirectors> getBoardOfDirectorsByCompanyId(String companyId) {
        logger.info("Retrieving board of directors for company ID: {}", companyId);
        return repository.findBySymbolWithVersionAndTime(companyId)
                .map(mapper::toModel);
    }
    
    @Override
    public BoardOfDirectors saveBoardOfDirectors(BoardOfDirectors boardOfDirectors) {
        logger.info("Saving board of directors for company ID: {}", boardOfDirectors.getSymbol());
        BoardOfDirectorsDocument document = mapper.toDocument(boardOfDirectors);
        BoardOfDirectorsDocument savedDocument = repository.save(document);
        return mapper.toModel(savedDocument);
    }
}
