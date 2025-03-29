package com.am.common.investment.persistence.repository.stock.financial;

import java.util.UUID;

import com.am.common.investment.persistence.document.stock.financial.cashflow.CashFlowDocument;
import com.am.common.investment.persistence.repository.financial.BaseDocumentRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface CashFlowRepository extends BaseDocumentRepository<CashFlowDocument, UUID> {
}
