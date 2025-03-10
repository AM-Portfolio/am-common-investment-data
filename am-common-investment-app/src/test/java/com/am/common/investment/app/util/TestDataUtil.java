package com.am.common.investment.app.util;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.model.equity.MarketData;
import com.am.common.investment.model.equity.MarketIndexIndices;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestDataUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    
    public static EquityPrice createEquityPrice(String symbol, String isin, Double open, Double high, Double low, 
            Double close, Long volume, String exchange, String currency, Instant time) {
        return EquityPrice.builder()
            .symbol(symbol)
            .isin(isin)
            .open(open)
            .high(high)
            .low(low)
            .close(close)
            .volume(volume)
            .exchange(exchange)
            .currency(currency)
            .time(time)
            .build();
    }

    public static EquityPriceMeasurement createMeasurement(String symbol, String isin, Double open, Double high, Double low, 
            Double close, Long volume, String exchange, String currency, Instant time) {
        EquityPriceMeasurement measurement = new EquityPriceMeasurement();
        measurement.setSymbol(symbol);
        measurement.setIsin(isin);
        measurement.setOpen(open);
        measurement.setHigh(high);
        measurement.setLow(low);
        measurement.setClose(close);
        measurement.setVolume(volume);
        measurement.setExchange(exchange);
        measurement.setCurrency(currency);
        measurement.setTime(time);
        return measurement;
    }

    public static EquityPrice createSimpleEquityPrice(String symbol, Double close, Instant time) {
        return createEquityPrice(symbol, null, null, null, null, close, null, null, null, time);
    }

    public static EquityPriceMeasurement createSimpleMeasurement(String symbol, Double close, Instant time) {
        return createMeasurement(symbol, null, null, null, null, close, null, null, null, time);
    }

    public static List<MarketIndexIndices> loadMarketIndexIndicesFromJson() {
        // Create a sample test data since the JSON file doesn't exist
        MarketIndexIndices testData = MarketIndexIndices.builder()
            .key("BROAD MARKET INDICES")
            .index("NIFTY 50")
            .indexSymbol("NIFTY50")
            .timestamp(LocalDateTime.of(2025, 3, 9, 23, 0)) // Fixed timestamp for testing
            .marketData(MarketData.builder()
                .open(19500.0)
                .high(19750.0)
                .low(19450.0)
                .last(19650.0)
                .previousClose(19400.0)
                .percentChange(1.29)
                .build())
            .build();
        return List.of(testData);
    }

    private static LocalDateTime parseDate(String date) {
        if (date == null || date.equals("-")) {
            return LocalDateTime.now().minusDays(30); // Default fallback
        }
        return LocalDateTime.of(
            LocalDate.parse(date, DATE_FORMATTER),
            LocalTime.MIDNIGHT
        );
    }

    private static double parseDouble(String value) {
        if (value == null || value.equals("-") || value.equals("")) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }
}
