package com.am.common.investment.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.am.common.investment.model.board.BoardOfDirectors;
import com.am.common.investment.model.equity.financial.balancesheet.BalanceSheet;
import com.am.common.investment.model.equity.financial.cashflow.CashFlow;
import com.am.common.investment.model.equity.financial.factsheetdividend.FactSheetDividend;
import com.am.common.investment.model.equity.financial.profitandloss.ProfitAndLoss;
import com.am.common.investment.model.equity.financial.resultstatement.FinancialResult;
import com.am.common.investment.persistence.document.BaseDocument;
import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;
import com.am.common.investment.persistence.document.stock.financial.balancesheet.BalanceSheetDocument;
import com.am.common.investment.persistence.document.stock.financial.cashflow.CashFlowDocument;
import com.am.common.investment.persistence.document.stock.financial.factsheetdividend.FactSheetDividendDocument;
import com.am.common.investment.persistence.document.stock.financial.profitandloss.ProfitAndLossDocument;
import com.am.common.investment.persistence.document.stock.financial.result.FinancialResultDocument;
import com.am.common.investment.persistence.repository.companyprofile.BoardOfDirectorsRepository;
import com.am.common.investment.persistence.repository.stock.financial.BalanceSheetRepository;
import com.am.common.investment.persistence.repository.stock.financial.CashFlowRepository;
import com.am.common.investment.persistence.repository.stock.financial.FactSheetRepository;
import com.am.common.investment.persistence.repository.stock.financial.FinancialResultRepository;
import com.am.common.investment.persistence.repository.stock.financial.ProfitAndLossRepository;
import com.am.common.investment.service.DocumentVersionService;
import com.am.common.investment.service.StockFinancialPerformanceService;
import com.am.common.investment.service.mapper.StockFinancialPerformanceMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockFinancialPerformanceServiceImpl implements StockFinancialPerformanceService {
    
    private final BoardOfDirectorsRepository boardOfDirectorsRepository;
    private final ProfitAndLossRepository profitAndLossRepository;
    private final BalanceSheetRepository balanceSheetRepository;
    private final FinancialResultRepository financialResultRepository;
    private final FactSheetRepository factSheetRepository;
    private final CashFlowRepository cashFlowRepository;
    private final StockFinancialPerformanceMapper mapper;
    private final DocumentVersionService<BaseDocument> versionService;
    
    @Override
    public Optional<BoardOfDirectors> getBoardOfDirectors(String symbol) {
        Optional<BoardOfDirectorsDocument> document = boardOfDirectorsRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public BoardOfDirectors saveBoardOfDirectors(BoardOfDirectors boardOfDirectors) {
        BoardOfDirectorsDocument document = mapper.toDocument(boardOfDirectors);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        BoardOfDirectorsDocument savedDocument = boardOfDirectorsRepository.save(document);
        return mapper.toModel(savedDocument);
    }
    
    @Override
    public Optional<ProfitAndLoss> getProfitAndLoss(String symbol) {
        Optional<ProfitAndLossDocument> document = profitAndLossRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public ProfitAndLoss saveProfitAndLoss(ProfitAndLoss profitAndLoss) {
        ProfitAndLossDocument document = mapper.toDocument(profitAndLoss);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        ProfitAndLossDocument savedDocument = profitAndLossRepository.save(document);
        return mapper.toModel(savedDocument);
    }
    
    @Override
    public Optional<BalanceSheet> getBalanceSheet(String symbol) {
        Optional<BalanceSheetDocument> document = balanceSheetRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public BalanceSheet saveBalanceSheet(BalanceSheet balanceSheet) {
        BalanceSheetDocument document = mapper.toDocument(balanceSheet);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        BalanceSheetDocument savedDocument = balanceSheetRepository.save(document);
        return mapper.toModel(savedDocument);
    }
    
    @Override
    public Optional<FinancialResult> getFinancialResult(String symbol) {
        Optional<FinancialResultDocument> document = financialResultRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public FinancialResult saveFinancialResult(FinancialResult financialResult) {
        FinancialResultDocument document = mapper.toDocument(financialResult);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        FinancialResultDocument savedDocument = financialResultRepository.save(document);
        return mapper.toModel(savedDocument);
    }
    
    @Override
    public Optional<FactSheetDividend> getFactSheetDividend(String symbol) {
        Optional<FactSheetDividendDocument> document = factSheetRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public FactSheetDividend saveFactSheetDividend(FactSheetDividend factSheetDividend) {
        FactSheetDividendDocument document = mapper.toDocument(factSheetDividend);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        FactSheetDividendDocument savedDocument = factSheetRepository.save(document);
        return mapper.toModel(savedDocument);
    }
    
    @Override
    public Optional<CashFlow> getCashFlow(String symbol) {
        Optional<CashFlowDocument> document = cashFlowRepository.findBySymbolWithVersionAndTime(symbol);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public CashFlow saveCashFlow(CashFlow cashFlow) {
        CashFlowDocument document = mapper.toDocument(cashFlow);
        
        // Increment version before saving
        versionService.incrementVersion(document);
        
        CashFlowDocument savedDocument = cashFlowRepository.save(document);
        return mapper.toModel(savedDocument);
    }
}
