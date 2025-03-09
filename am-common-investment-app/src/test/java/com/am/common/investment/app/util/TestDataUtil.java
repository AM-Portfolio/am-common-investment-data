package com.am.common.investment.app.util;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.model.equity.FundamentalRatios;
import com.am.common.investment.model.equity.HistoricalComparison;
import com.am.common.investment.model.equity.MarketBreadth;
import com.am.common.investment.model.equity.MarketData;
import com.am.common.investment.model.equity.MarketIndexIndices;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestDataUtil {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
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
        try {
            ClassPathResource resource = new ClassPathResource("market_index_Indices.json");
            List<MarketIndexData> jsonData = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<MarketIndexData>>() {}
            );
            return jsonData.stream()
                .map(TestDataUtil::convertToMarketIndexIndices)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load market index data from JSON", e);
        }
    }

    private static MarketIndexIndices convertToMarketIndexIndices(MarketIndexData data) {
        return MarketIndexIndices.builder()
            .key(data.getKey())
            .index(data.getIndex())
            .indexSymbol(data.getIndexSymbol())
            .timestamp(LocalDateTime.of(2025, 3, 9, 23, 0)) // Fixed timestamp for testing
            .marketData(MarketData.builder()
                .open(data.getOpen())
                .high(data.getHigh())
                .low(data.getLow())
                .last(data.getLast())
                .previousClose(data.getPreviousClose())
                .percentChange(data.getPercentChange())
                .build())
            .fundamentalRatios(FundamentalRatios.builder()
                .priceToEarningRation(parseDouble(data.getPe()))
                .priceToBookRation(parseDouble(data.getPb()))
                .dividenYield(parseDouble(data.getDy()))
                .build())
            .marketBreadth(MarketBreadth.builder()
                .advances(data.getAdvances())
                .declines(data.getDeclines())
                .unchanged(data.getUnchanged())
                .build())
            .historicalComparison(HistoricalComparison.builder()
                .value(data.getLast())
                .perChange365d(data.getPerChange365d())
                .date365dAgo(parseDate(data.getDate365dAgo()))
                .perChange30d(data.getPerChange30d())
                .date30dAgo(parseDate(data.getDate30dAgo()))
                .previousDay(data.getPreviousDay())
                .oneWeekAgo(data.getOneWeekAgo())
                .oneMonthAgo(data.getOneMonthAgo())
                .oneYearAgo(data.getOneYearAgo())
                .build())
            .build();
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

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MarketIndexData {
        private String key;
        private String index;
        private String indexSymbol;
        private Double last;
        private Double variation;
        private Double percentChange;
        private Double open;
        private Double high;
        private Double low;
        private Double previousClose;
        private Double yearHigh;
        private Double yearLow;
        private Double indicativeClose;
        private String pe;
        private String pb;
        private String dy;
        private String declines;
        private String advances;
        private String unchanged;
        private Double perChange365d;
        private String date365dAgo;
        private String chart365dPath;
        private String date30dAgo;
        private Double perChange30d;
        private String chart30dPath;
        private String chartTodayPath;
        private Double previousDay;
        private Double oneWeekAgo;
        private Double oneMonthAgo;
        private Double oneYearAgo;

        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setLast(Double last) { this.last = last; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setVariation(Double variation) { this.variation = variation; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setPercentChange(Double percentChange) { this.percentChange = percentChange; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setOpen(Double open) { this.open = open; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setHigh(Double high) { this.high = high; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setLow(Double low) { this.low = low; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setPreviousClose(Double previousClose) { this.previousClose = previousClose; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setYearHigh(Double yearHigh) { this.yearHigh = yearHigh; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setYearLow(Double yearLow) { this.yearLow = yearLow; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setIndicativeClose(Double indicativeClose) { this.indicativeClose = indicativeClose; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setPerChange365d(Double perChange365d) { this.perChange365d = perChange365d; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setPerChange30d(Double perChange30d) { this.perChange30d = perChange30d; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setPreviousDay(Double previousDay) { this.previousDay = previousDay; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setOneWeekAgo(Double oneWeekAgo) { this.oneWeekAgo = oneWeekAgo; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setOneMonthAgo(Double oneMonthAgo) { this.oneMonthAgo = oneMonthAgo; }
        @JsonDeserialize(using = SafeDoubleDeserializer.class)
        public void setOneYearAgo(Double oneYearAgo) { this.oneYearAgo = oneYearAgo; }

        // Regular getters and setters for non-numeric fields
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getIndex() { return index; }
        public void setIndex(String index) { this.index = index; }
        public String getIndexSymbol() { return indexSymbol; }
        public void setIndexSymbol(String indexSymbol) { this.indexSymbol = indexSymbol; }
        public String getPe() { return pe; }
        public void setPe(String pe) { this.pe = pe; }
        public String getPb() { return pb; }
        public void setPb(String pb) { this.pb = pb; }
        public String getDy() { return dy; }
        public void setDy(String dy) { this.dy = dy; }
        public String getDeclines() { return declines; }
        public void setDeclines(String declines) { this.declines = declines; }
        public String getAdvances() { return advances; }
        public void setAdvances(String advances) { this.advances = advances; }
        public String getUnchanged() { return unchanged; }
        public void setUnchanged(String unchanged) { this.unchanged = unchanged; }
        public String getDate365dAgo() { return date365dAgo; }
        public void setDate365dAgo(String date365dAgo) { this.date365dAgo = date365dAgo; }
        public String getChart365dPath() { return chart365dPath; }
        public void setChart365dPath(String chart365dPath) { this.chart365dPath = chart365dPath; }
        public String getDate30dAgo() { return date30dAgo; }
        public void setDate30dAgo(String date30dAgo) { this.date30dAgo = date30dAgo; }
        public String getChart30dPath() { return chart30dPath; }
        public void setChart30dPath(String chart30dPath) { this.chart30dPath = chart30dPath; }
        public String getChartTodayPath() { return chartTodayPath; }
        public void setChartTodayPath(String chartTodayPath) { this.chartTodayPath = chartTodayPath; }

        // Getters for numeric fields
        public Double getLast() { return last; }
        public Double getVariation() { return variation; }
        public Double getPercentChange() { return percentChange; }
        public Double getOpen() { return open; }
        public Double getHigh() { return high; }
        public Double getLow() { return low; }
        public Double getPreviousClose() { return previousClose; }
        public Double getYearHigh() { return yearHigh; }
        public Double getYearLow() { return yearLow; }
        public Double getIndicativeClose() { return indicativeClose; }
        public Double getPerChange365d() { return perChange365d; }
        public Double getPerChange30d() { return perChange30d; }
        public Double getPreviousDay() { return previousDay; }
        public Double getOneWeekAgo() { return oneWeekAgo; }
        public Double getOneMonthAgo() { return oneMonthAgo; }
        public Double getOneYearAgo() { return oneYearAgo; }
    }

    private static class SafeDoubleDeserializer extends JsonDeserializer<Double> {
        @Override
        public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            String value = p.getValueAsString();
            if (value == null || value.trim().isEmpty() || value.equals("-")) {
                return null;
            }
            try {
                return Double.valueOf(value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
