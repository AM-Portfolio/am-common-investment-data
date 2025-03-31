package com.am.common.investment.service;

import com.am.common.investment.model.board.BoardOfDirectors;
import com.am.common.investment.model.equity.financial.balancesheet.BalanceSheet;
import com.am.common.investment.model.equity.financial.cashflow.CashFlow;
import com.am.common.investment.model.equity.financial.factsheetdividend.FactSheetDividend;
import com.am.common.investment.model.equity.financial.profitandloss.ProfitAndLoss;
import com.am.common.investment.model.equity.financial.resultstatement.QuaterlyResult;

import java.util.Optional;

/**
 * Service for managing stock financial performance data
 */
public interface StockFinancialPerformanceService {
    Optional<BoardOfDirectors> getBoardOfDirectors(String symbol);
    BoardOfDirectors saveBoardOfDirectors(BoardOfDirectors boardOfDirectors);

    Optional<ProfitAndLoss> getProfitAndLoss(String symbol);
    ProfitAndLoss saveProfitAndLoss(ProfitAndLoss profitAndLoss);

    Optional<BalanceSheet> getBalanceSheet(String symbol);
    BalanceSheet saveBalanceSheet(BalanceSheet balanceSheet);

    Optional<FactSheetDividend> getFactSheetDividend(String symbol);
    FactSheetDividend saveFactSheetDividend(FactSheetDividend factSheetDividend);

    Optional<CashFlow> getCashFlow(String symbol);
    CashFlow saveCashFlow(CashFlow cashFlow);

    Optional<QuaterlyResult> getQuaterlyResult(String symbol);
    QuaterlyResult saveQuaterlyResult(QuaterlyResult quaterlyResult);
}
