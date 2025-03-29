package com.am.common.investment.persistence.repository.stock.financial;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BalanceSheetRepository extends MongoRepository<BalanceSheetDocument, String> {
    
}
