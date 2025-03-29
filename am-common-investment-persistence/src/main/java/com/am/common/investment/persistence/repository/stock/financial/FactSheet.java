package com.am.common.investment.persistence.repository.stock.financial;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.am.common.investment.persistence.document.stock.financial.BalanceSheet;

public interface BalanceSheetRepository extends MongoRepository<BalanceSheet, String> {
    
}
