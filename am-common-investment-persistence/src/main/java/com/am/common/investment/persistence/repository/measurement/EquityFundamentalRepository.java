package com.am.common.investment.persistence.repository.measurement;

import com.am.common.investment.persistence.influx.measurement.EquityFundamental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface EquityFundamentalRepository extends JpaRepository<EquityFundamental, Long> {
    
    // Find by symbol
    @Query("SELECT e FROM EquityFundamentalMeasurement e WHERE e.symbol = :symbol ORDER BY e.time DESC LIMIT 1")
    Optional<EquityFundamental> findLatestBySymbol(@Param("symbol") String symbol);
    
    List<EquityFundamental> findBySymbolOrderByTimeDesc(String symbol);
    
    List<EquityFundamental> findBySymbolAndTimeBetweenOrderByTimeDesc(String symbol, Instant startTime, Instant endTime);
    
    // Find by ISIN
    @Query("SELECT e FROM EquityFundamentalMeasurement e WHERE e.isin = :isin ORDER BY e.time DESC LIMIT 1")
    Optional<EquityFundamental> findLatestByIsin(@Param("isin") String isin);
    
    List<EquityFundamental> findByIsinOrderByTimeDesc(String isin);
    
    List<EquityFundamental> findByIsinAndTimeBetweenOrderByTimeDesc(String isin, Instant startTime, Instant endTime);
    
    // Find latest by symbol or ISIN
    default Optional<EquityFundamental> findLatestByKey(String key) {
        Optional<EquityFundamental> bySymbol = findLatestBySymbol(key);
        if (bySymbol.isPresent()) {
            return bySymbol;
        }
        return findLatestByIsin(key);
    }
    
    // Advanced queries
    List<EquityFundamental> findByMarketCapGreaterThan(Double marketCap);
    List<EquityFundamental> findByPeLessThan(Double pe);
    List<EquityFundamental> findByDividendYieldGreaterThan(Double dividendYield);
    
    // Aggregation queries
    Double findAveragePeBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime);
    Double findMaxDividendYieldBySymbol(String symbol);
    Double findMinPeBySymbol(String symbol);
}
