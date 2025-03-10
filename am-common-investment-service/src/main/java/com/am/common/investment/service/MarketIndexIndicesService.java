package com.am.common.investment.service;

import com.am.common.investment.model.equity.MarketIndexIndices;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MarketIndexIndicesService {
    // Basic operations
    void save(MarketIndexIndices indices);
    List<MarketIndexIndices> getByKey(String key);
}
