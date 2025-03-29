package com.am.common.investment.service;

import com.am.common.investment.model.board.BoardOfDirectors;
import com.am.common.investment.model.equity.financial.balancesheet.BalanceSheet;
import com.am.common.investment.model.equity.financial.cashflow.CashFlow;
import com.am.common.investment.model.equity.financial.factsheetdividend.FactSheetDividend;
import com.am.common.investment.model.equity.financial.profitandloss.ProfitAndLoss;
import com.am.common.investment.model.equity.financial.resultstatement.FinancialResult;

import java.util.Optional;

/**
 * Service for managing stock financial performance data
 */
public interface StockFinancialPerformanceService {
    Optional<BoardOfDirectors> getStockFinancialPerformanceByCompanyId(String companyId);
    BoardOfDirectors saveStockFinancialPerformance(BoardOfDirectors stockFinancialPerformance);

    Optional<ProfitAndLoss> getProfitAndLoss(String symbol);
    ProfitAndLoss saveProfitAndLoss(ProfitAndLoss profitAndLoss);

    Optional<BalanceSheet> getBalanceSheet(String symbol);
    BalanceSheet saveBalanceSheet(BalanceSheet balanceSheet);

    Optional<FinancialResult> getFinancialResult(String symbol);
    FinancialResult saveFinancialResult(FinancialResult financialResult);

    Optional<FactSheetDividend> getFactSheetDividend(String symbol);
    FactSheetDividend saveFactSheetDividend(FactSheetDividend factSheetDividend);

    Optional<CashFlow> getCashFlow(String symbol);
    CashFlow saveCashFlow(CashFlow cashFlow);
}
