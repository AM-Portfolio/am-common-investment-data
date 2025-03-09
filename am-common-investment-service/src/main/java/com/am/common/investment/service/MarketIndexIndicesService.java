package com.am.common.investment.service;

import com.am.common.investment.model.equity.MarketIndexIndices;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MarketIndexIndicesService {
    // Basic operations
    void save(MarketIndexIndices indices);
    void saveAll(List<MarketIndexIndices> indices);

    // Query by index
    Optional<MarketIndexIndices> getLatestByIndex(String index);
    List<MarketIndexIndices> getByIndex(String index);
    List<MarketIndexIndices> getByIndexAndTimeRange(String index, Instant startTime, Instant endTime);

    // Query by index symbol
    Optional<MarketIndexIndices> getLatestByIndexSymbol(String indexSymbol);
    List<MarketIndexIndices> getByIndexSymbol(String indexSymbol);
    List<MarketIndexIndices> getByIndexSymbolAndTimeRange(String indexSymbol, Instant startTime, Instant endTime);

    // Query by key
    Optional<MarketIndexIndices> getLatestByKey(String key);
    List<MarketIndexIndices> getByKey(String key);
    List<MarketIndexIndices> getByKeyAndTimeRange(String key, Instant startTime, Instant endTime);
}
