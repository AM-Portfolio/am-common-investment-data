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
    
    @Override
    public Optional<BoardOfDirectors> getStockFinancialPerformanceByCompanyId(String companyId) {
        Optional<BoardOfDirectorsDocument> document = boardOfDirectorsRepository.findBySymbolWithVersionAndTime(companyId);
        return document.map(mapper::toModel);
    }
    
    @Override
    @Transactional
    public BoardOfDirectors saveStockFinancialPerformance(BoardOfDirectors stockFinancialPerformance) {
        BoardOfDirectorsDocument document = mapper.toDocument(stockFinancialPerformance);
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
        CashFlowDocument savedDocument = cashFlowRepository.save(document);
        return mapper.toModel(savedDocument);
    }
}
