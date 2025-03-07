package com.am.common.investment.app.service;

import static com.am.common.investment.app.constant.AppConstants.InfluxDB.*;
import static com.am.common.investment.app.constant.AppConstants.TestData.*;
import static com.am.common.investment.app.constant.AppConstants.Query.*;
import static com.am.common.investment.app.util.TestAssertionUtil.*;

import com.am.common.investment.app.InvestmentDataApplication;
import com.am.common.investment.app.config.TestConfig;
import com.am.common.investment.app.config.TestContainersConfig;
import com.am.common.investment.app.util.TestDataUtil;
import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import com.am.common.investment.service.EquityService;
import com.influxdb.client.InfluxDBClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {
    InvestmentDataApplication.class,
    TestConfig.class,
    TestContainersConfig.class
})
@ActiveProfiles("test")
@Testcontainers
@ExtendWith(TestContainersConfig.class)
public class EquityServiceIntegrationTest {

    @Autowired
    private EquityService equityService;

    @Autowired
    private InfluxDBClient influxDBClient;

    @BeforeEach
    void cleanup() {
        // Delete all data in the bucket before each test
        influxDBClient.getDeleteApi().delete(
            OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC),
            OffsetDateTime.now(ZoneOffset.UTC),
            String.format("_measurement=\"%s\"", MEASUREMENT_EQUITY_PRICE),
            BUCKET,
            ORG
        );
    }

    @Test
    void shouldSaveAndRetrieveEquityPrice() throws InterruptedException {
        // Given
        double open = 149.0;
        double high = 151.0;
        double low = 148.0;
        double close = 150.0;
        long volume = 1000000L;
        Instant timestamp = Instant.now();

        EquityPrice equityPrice = TestDataUtil.createEquityPrice(
            SYMBOL_AAPL, ISIN_AAPL, open, high, low, close, volume, 
            EXCHANGE_NASDAQ, CURRENCY_USD, timestamp
        );

        // When
        equityService.savePrice(equityPrice);
        
        // Add a small delay to ensure InfluxDB processes the write
        Thread.sleep(1000);

        // Verify directly with InfluxDB client
        String query = String.format(EQUITY_PRICE_QUERY_TEMPLATE,
            BUCKET, 1, "h", MEASUREMENT_EQUITY_PRICE, SYMBOL_AAPL);
            
        List<EquityPriceMeasurement> directResults = influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
        
        if (!directResults.isEmpty()) {
            EquityPriceMeasurement measurement = directResults.get(0);
            measurement.setSymbol(SYMBOL_AAPL);
            measurement.setIsin(ISIN_AAPL);
            measurement.setCurrency(CURRENCY_USD);
        }
        
        System.out.println("Direct InfluxDB query results: " + directResults);
        
        Optional<EquityPrice> retrievedPrice = equityService.getLatestPrice(SYMBOL_AAPL);

        // Then
        assertEquityPrice(retrievedPrice, equityPrice);
    }
}
