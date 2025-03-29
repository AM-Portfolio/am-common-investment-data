package com.am.common.investment.persistence.repository.stock.financial;

import java.util.UUID;

import com.am.common.investment.persistence.document.stock.financial.factsheetdividend.FactSheetDividendDocument;
import com.am.common.investment.persistence.repository.financial.BaseDocumentRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FactSheetRepository extends BaseDocumentRepository<FactSheetDividendDocument, UUID> {
}
