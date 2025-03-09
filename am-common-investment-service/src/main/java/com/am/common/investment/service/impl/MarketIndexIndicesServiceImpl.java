package com.am.common.investment.service.impl;

import com.am.common.investment.model.equity.*;
import com.am.common.investment.persistence.influx.measurement.MarketIndexIndicesMeasurement;
import com.am.common.investment.persistence.repository.measurement.MarketIndexIndicesRepository;
import com.am.common.investment.service.MarketIndexIndicesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketIndexIndicesServiceImpl implements MarketIndexIndicesService {
    private static final Logger logger = LoggerFactory.getLogger(MarketIndexIndicesServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private final MarketIndexIndicesRepository marketIndexRepository;

    @Override
    public void save(MarketIndexIndices indices) {
        logger.debug("Writing point to InfluxDB: measurement=market_index, key={}, index={}, symbol={}", 
            indices.getKey(), indices.getIndex(), indices.getIndexSymbol());
        
        marketIndexRepository.save(convertToMeasurement(indices));
        logger.debug("Successfully wrote point to InfluxDB");
    }

    @Override
    public void saveAll(List<MarketIndexIndices> indices) {
        if (indices == null || indices.isEmpty()) {
            logger.warn("No indices provided to save");
            return;
        }

        logger.debug("Starting batch save of {} market index indices", indices.size());
        marketIndexRepository.saveAll(indices.stream()
            .map(this::convertToMeasurement)
            .collect(Collectors.toList()));
    }

    @Override
    public Optional<MarketIndexIndices> getLatestByIndex(String index) {
        logger.debug("Finding latest index by index: {}", index);
        return marketIndexRepository.findLatestByIndex(index)
            .map(this::convertToModel);
    }

    @Override
    public List<MarketIndexIndices> getByIndex(String index) {
        logger.debug("Finding all indices by index: {}", index);
        return marketIndexRepository.findByIndex(index).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<MarketIndexIndices> getByIndexAndTimeRange(String index, Instant startTime, Instant endTime) {
        logger.debug("Finding indices by index and time range: index={}, startTime={}, endTime={}", 
            index, startTime, endTime);
        return marketIndexRepository.findByIndexAndTimeBetween(index, startTime, endTime).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<MarketIndexIndices> getLatestByIndexSymbol(String indexSymbol) {
        logger.debug("Finding latest index by symbol: {}", indexSymbol);
        return marketIndexRepository.findLatestByIndexSymbol(indexSymbol)
            .map(this::convertToModel);
    }

    @Override
    public List<MarketIndexIndices> getByIndexSymbol(String indexSymbol) {
        logger.debug("Finding all indices by symbol: {}", indexSymbol);
        return marketIndexRepository.findByIndexSymbol(indexSymbol).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<MarketIndexIndices> getByIndexSymbolAndTimeRange(String indexSymbol, Instant startTime, Instant endTime) {
        logger.debug("Finding indices by symbol and time range: symbol={}, startTime={}, endTime={}", 
            indexSymbol, startTime, endTime);
        return marketIndexRepository.findByIndexSymbolAndTimeBetween(indexSymbol, startTime, endTime).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public Optional<MarketIndexIndices> getLatestByKey(String key) {
        logger.debug("Finding latest index by key: {}", key);
        return marketIndexRepository.findLatestByKey(key)
            .map(this::convertToModel);
    }

    @Override
    public List<MarketIndexIndices> getByKey(String key) {
        logger.debug("Finding all indices by key: {}", key);
        return marketIndexRepository.findByKey(key).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<MarketIndexIndices> getByKeyAndTimeRange(String key, Instant startTime, Instant endTime) {
        logger.debug("Finding indices by key and time range: key={}, startTime={}, endTime={}", 
            key, startTime, endTime);
        return marketIndexRepository.findByKeyAndTimeRange(key, startTime, endTime).stream()
            .map(this::convertToModel)
            .collect(Collectors.toList());
    }

    private MarketIndexIndicesMeasurement convertToMeasurement(MarketIndexIndices indices) {
        MarketIndexIndicesMeasurement measurement = new MarketIndexIndicesMeasurement();
        measurement.setKey(indices.getKey());
        measurement.setIndex(indices.getIndex());
        measurement.setIndexSymbol(indices.getIndexSymbol());
        measurement.setTime(indices.getTimestamp().toInstant(ZoneOffset.UTC));

        // Market Data
        MarketData marketData = indices.getMarketData();
        if (marketData != null) {
            measurement.setMarketDataOpen(marketData.getOpen());
            measurement.setMarketDataPreviousClose(marketData.getPreviousClose());
            measurement.setMarketDataHigh(marketData.getHigh());
            measurement.setMarketDataLow(marketData.getLow());
            measurement.setMarketDataClose(marketData.getLast());
            measurement.setMarketDataPercentageChange(marketData.getPercentChange());
        }

        // Fundamental Ratios
        FundamentalRatios fundamentalRatios = indices.getFundamentalRatios();
        if (fundamentalRatios != null) {
            measurement.setFundamentalPriceEarning(fundamentalRatios.getPriceToEarningRation());
            measurement.setFundamentalPriceBook(fundamentalRatios.getPriceToBookRation());
            measurement.setFundamentalDividendYield(fundamentalRatios.getDividenYield());
        }

        // Market Breadth
        MarketBreadth marketBreadth = indices.getMarketBreadth();
        if (marketBreadth != null) {
            measurement.setMarketBreadthAdvances(Long.valueOf(marketBreadth.getAdvances()));
            measurement.setMarketBreadthDeclines(Long.valueOf(marketBreadth.getDeclines()));
            measurement.setMarketBreadthUnchanged(Long.valueOf(marketBreadth.getUnchanged()));
        }

        // Historical Comparison
        HistoricalComparison historicalComparison = indices.getHistoricalComparison();
        if (historicalComparison != null) {
            measurement.setHistoricalComparisonValue(historicalComparison.getValue());
            measurement.setHistoricalComparisonPerChange365d(historicalComparison.getPerChange365d());
            measurement.setHistoricalComparisonDate365dAgo(historicalComparison.getDate365dAgo().format(DATE_FORMATTER));
            measurement.setHistoricalComparisonPerChange30d(historicalComparison.getPerChange30d());
            measurement.setHistoricalComparisonDate30dAgo(historicalComparison.getDate30dAgo().format(DATE_FORMATTER));
            measurement.setHistoricalComparisonPreviousDay(historicalComparison.getPreviousDay());
            measurement.setHistoricalComparisonOneWeekAgo(historicalComparison.getOneWeekAgo());
            measurement.setHistoricalComparisonOneMonthAgo(historicalComparison.getOneMonthAgo());
            measurement.setHistoricalComparisonOneYearAgo(historicalComparison.getOneYearAgo());
        }

        return measurement;
    }

    private MarketIndexIndices convertToModel(MarketIndexIndicesMeasurement measurement) {
        try {
            return MarketIndexIndices.builder()
                .key(measurement.getKey())
                .index(measurement.getIndex())
                .indexSymbol(measurement.getIndexSymbol())
                .timestamp(LocalDateTime.ofInstant(measurement.getTime(), ZoneOffset.UTC))
                .marketData(MarketData.builder()
                    .open(measurement.getMarketDataOpen())
                    .previousClose(measurement.getMarketDataPreviousClose())
                    .high(measurement.getMarketDataHigh())
                    .low(measurement.getMarketDataLow())
                    .last(measurement.getMarketDataClose())
                    .percentChange(measurement.getMarketDataPercentageChange())
                    .build())
                .fundamentalRatios(FundamentalRatios.builder()
                    .priceToEarningRation(measurement.getFundamentalPriceEarning())
                    .priceToBookRation(measurement.getFundamentalPriceBook())
                    .dividenYield(measurement.getFundamentalDividendYield())
                    .build())
                .marketBreadth(MarketBreadth.builder()
                    .advances(measurement.getMarketBreadthAdvances() != null ? measurement.getMarketBreadthAdvances().toString() : "0")
                    .declines(measurement.getMarketBreadthDeclines() != null ? measurement.getMarketBreadthDeclines().toString() : "0")
                    .unchanged(measurement.getMarketBreadthUnchanged() != null ? measurement.getMarketBreadthUnchanged().toString() : "0")
                    .build())
                .historicalComparison(HistoricalComparison.builder()
                    .value(measurement.getHistoricalComparisonValue())
                    .perChange365d(measurement.getHistoricalComparisonPerChange365d())
                    .date365dAgo(parseDate(measurement.getHistoricalComparisonDate365dAgo()))
                    .perChange30d(measurement.getHistoricalComparisonPerChange30d())
                    .date30dAgo(parseDate(measurement.getHistoricalComparisonDate30dAgo()))
                    .previousDay(measurement.getHistoricalComparisonPreviousDay())
                    .oneWeekAgo(measurement.getHistoricalComparisonOneWeekAgo())
                    .oneMonthAgo(measurement.getHistoricalComparisonOneMonthAgo())
                    .oneYearAgo(measurement.getHistoricalComparisonOneYearAgo())
                    .build())
                .build();
        } catch (Exception e) {
            logger.error("Failed to convert measurement to model", e);
            throw e;
        }
    }

    private LocalDateTime parseDate(String date) {
        if (date == null || date.isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.of(LocalDateTime.parse(date, DATE_FORMATTER).toLocalDate(), LocalDateTime.now().toLocalTime());
        } catch (DateTimeParseException e) {
            logger.warn("Failed to parse date: {}, using current date", date);
            return LocalDateTime.now();
        }
    }
}
