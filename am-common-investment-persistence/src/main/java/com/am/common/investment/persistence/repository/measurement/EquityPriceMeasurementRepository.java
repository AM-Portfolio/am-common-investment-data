package com.am.common.investment.persistence.repository.measurement;

import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EquityPriceMeasurementRepository extends Repository<EquityPriceMeasurement, String> {
    
    // Basic CRUD operations
    void save(EquityPriceMeasurement measurement);
    //void saveAll(Iterable<EquityPriceMeasurement> measurements);
    
    // Find by symbol
    Optional<EquityPriceMeasurement> findLatestBySymbol(String symbol);
    List<EquityPriceMeasurement> findBySymbol(String symbol);
    List<EquityPriceMeasurement> findBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    
    // Find by ISIN
    Optional<EquityPriceMeasurement> findLatestByIsin(String isin);
    List<EquityPriceMeasurement> findByIsin(String isin);
    List<EquityPriceMeasurement> findByIsinAndTimeBetween(String isin, Instant startTime, Instant endTime);
    
    // Find by exchange and currency
    List<EquityPriceMeasurement> findByExchange(String exchange);
    List<EquityPriceMeasurement> findByCurrency(String currency);
    
    // Advanced queries
    List<EquityPriceMeasurement> findByVolumeGreaterThan(Long volume);
    List<EquityPriceMeasurement> findBySymbolAndCloseGreaterThan(String symbol, Double price);
    
    // Price range queries
    List<EquityPriceMeasurement> findBySymbolAndCloseBetween(String symbol, Double minPrice, Double maxPrice);
    
    // Aggregation queries
    Double findHighestPriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findLowestPriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findAverageVolumeBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findAverageClosePriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);

}
