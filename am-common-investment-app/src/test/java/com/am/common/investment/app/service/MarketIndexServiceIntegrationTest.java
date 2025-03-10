package com.am.common.investment.app.service;

import static com.am.common.investment.app.constant.AppConstants.InfluxDB.*;
import static com.am.common.investment.app.constant.AppConstants.Query.MARKET_INDEX_QUERY_TEMPLATE;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.am.common.investment.app.InvestmentDataApplication;
import com.am.common.investment.app.config.TestConfig;
import com.am.common.investment.app.config.TestContainersConfig;
import com.am.common.investment.app.util.TestAssertionUtil;
import com.am.common.investment.app.util.TestDataUtil;
import com.am.common.investment.model.equity.MarketIndexIndices;
import com.am.common.investment.persistence.influx.measurement.MarketIndexIndicesMeasurement;
import com.am.common.investment.persistence.repository.measurement.MarketIndexIndicesRepository;
import com.am.common.investment.service.MarketIndexIndicesService;
import com.influxdb.client.InfluxDBClient;

@SpringBootTest(classes = {
    InvestmentDataApplication.class,
    TestConfig.class,
    TestContainersConfig.class
})
@ActiveProfiles("test")
@Testcontainers
@ExtendWith(TestContainersConfig.class)
public class MarketIndexServiceIntegrationTest {

    @Autowired
    private MarketIndexIndicesService marketIndexService;

    @Autowired
    private InfluxDBClient influxDBClient;  

    private List<MarketIndexIndices> testData;

    @BeforeEach
    void setup() {
        // Delete all data in the bucket
        influxDBClient.getDeleteApi().delete(
            OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.of(2050, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
            String.format("_measurement=\"%s\"", MEASUREMENT_MARKET_INDEX),
            BUCKET,
            ORG
        );
        
        // Add a small delay to ensure delete is processed
        try {
            Thread.sleep(1000);
            // Load test data from JSON
            testData = TestDataUtil.loadMarketIndexIndicesFromJson();
            System.out.println("Cleaned up all test data from InfluxDB");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    void shouldSaveAndRetrieveMarketIndex() throws InterruptedException {
        // Given
        MarketIndexIndices nifty50 = testData.stream()
            .filter(index -> index.getKey().equals("BROAD MARKET INDICES"))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Test data not found for NIFTY 50"));

        System.out.println("Test data to save:");
        System.out.println("  - Key: " + nifty50.getKey());
        System.out.println("  - Index: " + nifty50.getIndex());
        System.out.println("  - IndexSymbol: " + nifty50.getIndexSymbol());
        System.out.println("  - Timestamp: " + nifty50.getTimestamp());
        System.out.println("  - Market Data:");
        System.out.println("    - Open: " + nifty50.getMarketData().getOpen());
        System.out.println("    - High: " + nifty50.getMarketData().getHigh());
        System.out.println("    - Low: " + nifty50.getMarketData().getLow());
        System.out.println("    - Last: " + nifty50.getMarketData().getLast());
        System.out.println("    - Previous Close: " + nifty50.getMarketData().getPreviousClose());
        System.out.println("    - Percent Change: " + nifty50.getMarketData().getPercentChange());

        // When - Save through service
        marketIndexService.save(nifty50);
        Thread.sleep(2000); // Wait for write to complete

        // First try raw query without pivot to see what data exists
        String rawQuery = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -%d%s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\" and r.key == \"%s\") " +
            "|> yield()",
            BUCKET, 24, "h", MEASUREMENT_MARKET_INDEX, nifty50.getKey());
            
        System.out.println("\nExecuting raw InfluxDB query:");
        System.out.println(rawQuery);
        
        String rawResults = influxDBClient.getQueryApi().queryRaw(rawQuery);
        System.out.println("\nRaw Query Results:");
        System.out.println(rawResults);

        // Now try the full query with pivot
        String query = String.format(MARKET_INDEX_QUERY_TEMPLATE,
            BUCKET, 24, "h", MEASUREMENT_MARKET_INDEX, nifty50.getKey());
            
        System.out.println("\nExecuting InfluxDB query with pivot:");
        System.out.println(query);
        
        List<MarketIndexIndicesMeasurement> directResults = influxDBClient.getQueryApi().query(query, MarketIndexIndicesMeasurement.class);
        
        System.out.println("\nQuery results:");
        System.out.println("Number of results: " + directResults.size());
        
        if (!directResults.isEmpty()) {
            MarketIndexIndicesMeasurement measurement = directResults.get(0);
            measurement.setKey(nifty50.getKey());
            measurement.setIndex(nifty50.getIndex());
            measurement.setIndexSymbol(nifty50.getIndexSymbol());
            
            System.out.println("First result:");
            System.out.println("  - Key: " + measurement.getKey());
            System.out.println("  - Index: " + measurement.getIndex());
            System.out.println("  - IndexSymbol: " + measurement.getIndexSymbol());
            System.out.println("  - Market Data Open: " + measurement.getMarketDataOpen());
            System.out.println("  - Time: " + measurement.getTime());
        } else {
            System.out.println("No results found");
        }
    }
}
