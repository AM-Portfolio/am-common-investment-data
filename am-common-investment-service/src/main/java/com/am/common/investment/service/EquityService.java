package com.am.common.investment.service;

import com.am.common.investment.model.equity.EquityPrice;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EquityService {
    // Price methods
    void savePrice(EquityPrice price);
    Optional<EquityPrice> getLatestPrice(String symbol);
    List<EquityPrice> getPriceHistory(String symbol, Instant startTime, Instant endTime);
    List<EquityPrice> getPricesByExchange(String exchange);
    Double getHighestPrice(String symbol, Instant startTime, Instant endTime);
    Double getLowestPrice(String symbol, Instant startTime, Instant endTime);
    Double getAverageVolume(String symbol, Instant startTime, Instant endTime);
}
