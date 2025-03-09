package com.am.common.investment.persistence.repository.measurement;

import com.am.common.investment.persistence.influx.measurement.MarketIndexIndicesMeasurement;

import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MarketIndexIndicesRepository extends Repository<MarketIndexIndicesMeasurement, String> {
    
    // Basic CRUD operations
    void save(MarketIndexIndicesMeasurement measurement);
    void saveAll(List<MarketIndexIndicesMeasurement> measurements);
    
    // Find by index
    Optional<MarketIndexIndicesMeasurement> findLatestByIndex(String index);
    List<MarketIndexIndicesMeasurement> findByIndex(String index);
    List<MarketIndexIndicesMeasurement> findByIndexAndTimeBetween(String index, Instant startTime, Instant endTime);
    
    // Find by index symbol
    Optional<MarketIndexIndicesMeasurement> findLatestByIndexSymbol(String indexSymbol);
    List<MarketIndexIndicesMeasurement> findByIndexSymbol(String indexSymbol);
    List<MarketIndexIndicesMeasurement> findByIndexSymbolAndTimeBetween(String indexSymbol, Instant startTime, Instant endTime);

    // Find by key
    Optional<MarketIndexIndicesMeasurement> findLatestByKey(String key);
    List<MarketIndexIndicesMeasurement> findByKey(String key);
    List<MarketIndexIndicesMeasurement> findByKeyAndTimeRange(String key, Instant startTime, Instant endTime);
}
