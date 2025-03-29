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
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = BaseMapper.class)
public interface StockFinancialPerformanceMapper {
    StockFinancialPerformanceMapper INSTANCE = Mappers.getMapper(StockFinancialPerformanceMapper.class);

    BoardOfDirectors toModel(BoardOfDirectorsDocument document);
    BoardOfDirectorsDocument toDocument(BoardOfDirectors model);

    CashFlow toModel(CashFlowDocument document);
    CashFlowDocument toDocument(CashFlow model);

    BalanceSheet toModel(BalanceSheetDocument document);
    BalanceSheetDocument toDocument(BalanceSheet model);

    ProfitAndLoss toModel(ProfitAndLossDocument document);
    ProfitAndLossDocument toDocument(ProfitAndLoss model);

    FactSheetDividend toModel(FactSheetDividendDocument document);
    FactSheetDividendDocument toDocument(FactSheetDividend model);

    FinancialResult toModel(FinancialResultDocument document);
    FinancialResultDocument toDocument(FinancialResult model);
}