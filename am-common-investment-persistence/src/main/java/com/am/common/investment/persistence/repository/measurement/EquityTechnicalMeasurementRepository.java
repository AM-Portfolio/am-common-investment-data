package com.am.common.investment.persistence.repository.measurement;

import com.am.common.investment.persistence.influx.measurement.EquityTechnicalMeasurement;
import org.springframework.data.repository.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EquityTechnicalMeasurementRepository extends Repository<EquityTechnicalMeasurement, String> {
    
    // Basic CRUD operations
    void save(EquityTechnicalMeasurement measurement);
    void saveAll(Iterable<EquityTechnicalMeasurement> measurements);
    
    // Find by symbol
    Optional<EquityTechnicalMeasurement> findLatestBySymbol(String symbol);
    List<EquityTechnicalMeasurement> findBySymbol(String symbol);
    List<EquityTechnicalMeasurement> findBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    
    // Find by ISIN
    Optional<EquityTechnicalMeasurement> findLatestByIsin(String isin);
    List<EquityTechnicalMeasurement> findByIsin(String isin);
    List<EquityTechnicalMeasurement> findByIsinAndTimeBetween(String isin, Instant startTime, Instant endTime);
    
    // Technical indicator queries
    List<EquityTechnicalMeasurement> findBySymbolAndRsi14LessThan(String symbol, Double rsi);
    List<EquityTechnicalMeasurement> findBySymbolAndRsi14GreaterThan(String symbol, Double rsi);
    List<EquityTechnicalMeasurement> findBySymbolAndMacdHistogramGreaterThan(String symbol, Double threshold);
    
    // Moving average queries
    List<EquityTechnicalMeasurement> findBySymbolAndSma20GreaterThanSma50(String symbol);
    List<EquityTechnicalMeasurement> findBySymbolAndPriceAboveSma200(String symbol, Double currentPrice);
    
    // Bollinger Band queries
    List<EquityTechnicalMeasurement> findBySymbolAndPriceNearBollingerLower(String symbol, Double currentPrice, Double threshold);
    List<EquityTechnicalMeasurement> findBySymbolAndPriceNearBollingerUpper(String symbol, Double currentPrice, Double threshold);
    
    // Aggregation queries
    Double findAverageRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findMaxRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findMinRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findAverageAtr14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
}
