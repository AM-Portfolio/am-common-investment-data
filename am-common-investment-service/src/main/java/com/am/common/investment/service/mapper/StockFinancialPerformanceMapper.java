package com.am.common.investment.service.mapper;

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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class StockFinancialPerformanceMapper {

    // Board of Directors mapping
    public BoardOfDirectors toModel(BoardOfDirectorsDocument document) {
        if (document == null) {
            return null;
        }
        
        BoardOfDirectors model = new BoardOfDirectors();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        
        if (document.getDirectors() != null) {
            model.setDirectors(document.getDirectors());
        } else {
            model.setDirectors(new ArrayList<>());
        }
        
        return model;
    }
    
    public BoardOfDirectorsDocument toDocument(BoardOfDirectors model) {
        if (model == null) {
            return null;
        }
        
        BoardOfDirectorsDocument document = new BoardOfDirectorsDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        
        if (model.getDirectors() != null) {
            document.setDirectors(model.getDirectors());
        } else {
            document.setDirectors(new ArrayList<>());
        }
        
        return document;
    }

    // Cash Flow mapping
    public CashFlow toModel(CashFlowDocument document) {
        if (document == null) {
            return null;
        }
        
        CashFlow model = new CashFlow();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        model.setOperatingMetrics(document.getOperatingMetrics());
        model.setInvestingMetrics(document.getInvestingMetrics());
        model.setFinancingMetrics(document.getFinancingMetrics());
        model.setNetCashFlow(document.getNetCashFlow());
        model.setFreeCashFlow(document.getFreeCashFlow());
        
        return model;
    }
    
    public CashFlowDocument toDocument(CashFlow model) {
        if (model == null) {
            return null;
        }
        
        CashFlowDocument document = new CashFlowDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        document.setOperatingMetrics(model.getOperatingMetrics());
        document.setInvestingMetrics(model.getInvestingMetrics());
        document.setFinancingMetrics(model.getFinancingMetrics());
        document.setNetCashFlow(model.getNetCashFlow());
        document.setFreeCashFlow(model.getFreeCashFlow());
        
        return document;
    }

    // Balance Sheet mapping
    public BalanceSheet toModel(BalanceSheetDocument document) {
        if (document == null) {
            return null;
        }
        
        BalanceSheet model = new BalanceSheet();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        model.setAssets(document.getAssets());
        model.setLiabilitiesEquity(document.getLiabilitiesEquity());
        model.setTotalDebits(document.getTotalDebits());
        model.setAssetsMetrics(document.getAssetsMetrics());
        model.setLiabilitiesMetrics(document.getLiabilitiesMetrics());
        model.setEquityMetrics(document.getEquityMetrics());
        
        return model;
    }
    
    public BalanceSheetDocument toDocument(BalanceSheet model) {
        if (model == null) {
            return null;
        }
        
        BalanceSheetDocument document = new BalanceSheetDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        document.setAssets(model.getAssets());
        document.setLiabilitiesEquity(model.getLiabilitiesEquity());
        document.setTotalDebits(model.getTotalDebits());
        document.setAssetsMetrics(model.getAssetsMetrics());
        document.setLiabilitiesMetrics(model.getLiabilitiesMetrics());
        document.setEquityMetrics(model.getEquityMetrics());
        
        return document;
    }

    // Profit and Loss mapping
    public ProfitAndLoss toModel(ProfitAndLossDocument document) {
        if (document == null) {
            return null;
        }
        
        ProfitAndLoss model = new ProfitAndLoss();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        model.setTotalRevenue(document.getTotalRevenue());
        model.setOperatingRevenue(document.getOperatingRevenue());
        model.setCostMetrics(document.getCostMetrics());
        model.setProfitMetrics(document.getProfitMetrics());
        model.setGrowthMetrics(document.getGrowthMetrics());
        model.setEpsMetrics(document.getEpsMetrics());
        model.setTaxMetrics(document.getTaxMetrics());
        
        return model;
    }
    
    public ProfitAndLossDocument toDocument(ProfitAndLoss model) {
        if (model == null) {
            return null;
        }
        
        ProfitAndLossDocument document = new ProfitAndLossDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        document.setTotalRevenue(model.getTotalRevenue());
        document.setOperatingRevenue(model.getOperatingRevenue());
        document.setCostMetrics(model.getCostMetrics());
        document.setProfitMetrics(model.getProfitMetrics());
        document.setGrowthMetrics(model.getGrowthMetrics());
        document.setEpsMetrics(model.getEpsMetrics());
        document.setTaxMetrics(model.getTaxMetrics());
        
        return document;
    }

    // FactSheet Dividend mapping
    public FactSheetDividend toModel(FactSheetDividendDocument document) {
        if (document == null) {
            return null;
        }
        
        FactSheetDividend model = new FactSheetDividend();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        model.setGrowthMetrics(document.getGrowthMetrics());
        model.setFinancialRatios(document.getFinancialRatios());
        model.setDividendMetrics(document.getDividendMetrics());
        model.setAssetTurnoverRatio(document.getAssetTurnoverRatio());
        model.setWorkingCapitalDays(document.getWorkingCapitalDays());
        model.setInventoryTurnoverRatio(document.getInventoryTurnoverRatio());
        model.setAdjEarningsPerShare(document.getAdjEarningsPerShare());
        model.setEnterpriseValue(document.getEnterpriseValue());
        model.setPegRatio(document.getPegRatio());
        model.setPriceSalesRatio(document.getPriceSalesRatio());
        model.setAdjDividendPerShare(document.getAdjDividendPerShare());
        model.setFreeCashFlowPerShare(document.getFreeCashFlowPerShare());
        model.setCashConversionCycle(document.getCashConversionCycle());
        model.setFreeCashFlowYield(document.getFreeCashFlowYield());
        
        return model;
    }
    
    public FactSheetDividendDocument toDocument(FactSheetDividend model) {
        if (model == null) {
            return null;
        }
        
        FactSheetDividendDocument document = new FactSheetDividendDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        document.setGrowthMetrics(model.getGrowthMetrics());
        document.setFinancialRatios(model.getFinancialRatios());
        document.setDividendMetrics(model.getDividendMetrics());
        document.setAssetTurnoverRatio(model.getAssetTurnoverRatio());
        document.setWorkingCapitalDays(model.getWorkingCapitalDays());
        document.setInventoryTurnoverRatio(model.getInventoryTurnoverRatio());
        document.setAdjEarningsPerShare(model.getAdjEarningsPerShare());
        document.setEnterpriseValue(model.getEnterpriseValue());
        document.setPegRatio(model.getPegRatio());
        document.setPriceSalesRatio(model.getPriceSalesRatio());
        document.setAdjDividendPerShare(model.getAdjDividendPerShare());
        document.setFreeCashFlowPerShare(model.getFreeCashFlowPerShare());
        document.setCashConversionCycle(model.getCashConversionCycle());
        document.setFreeCashFlowYield(model.getFreeCashFlowYield());
        
        return document;
    }

    // Financial Result mapping
    public FinancialResult toModel(FinancialResultDocument document) {
        if (document == null) {
            return null;
        }
        
        FinancialResult model = new FinancialResult();
        model.setId(document.getId());
        model.setSymbol(document.getSymbol());
        model.setVersion(document.getVersion());
        model.setAudit(document.getAudit());
        model.setSource(document.getSource());
        model.setTotalRevenue(document.getTotalRevenue());
        model.setOperatingRevenue(document.getOperatingRevenue());
        model.setCostMetrics(document.getCostMetrics());
        model.setProfitMetrics(document.getProfitMetrics());
        model.setGrowthMetrics(document.getGrowthMetrics());
        model.setEpsMetrics(document.getEpsMetrics());
        model.setTaxMetrics(document.getTaxMetrics());
        
        return model;
    }
    
    public FinancialResultDocument toDocument(FinancialResult model) {
        if (model == null) {
            return null;
        }
        
        FinancialResultDocument document = new FinancialResultDocument();
        document.setId(UUID.randomUUID());
        document.setSymbol(model.getSymbol());
        document.setVersion(model.getVersion());
        document.setAudit(model.getAudit());
        document.setSource(model.getSource());
        document.setTotalRevenue(model.getTotalRevenue());
        document.setOperatingRevenue(model.getOperatingRevenue());
        document.setCostMetrics(model.getCostMetrics());
        document.setProfitMetrics(model.getProfitMetrics());
        document.setGrowthMetrics(model.getGrowthMetrics());
        document.setEpsMetrics(model.getEpsMetrics());
        document.setTaxMetrics(model.getTaxMetrics());
        
        return document;
    }
}