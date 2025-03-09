package com.am.common.investment.app.util;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.model.equity.MarketIndexIndices;
import com.am.common.investment.model.equity.MarketData;
import com.am.common.investment.model.equity.FundamentalRatios;
import com.am.common.investment.model.equity.MarketBreadth;
import com.am.common.investment.model.equity.HistoricalComparison;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class TestAssertionUtil {
    
    public static void assertEquityPrice(Optional<EquityPrice> actual, EquityPrice expected) {
        assertThat(actual).isPresent();
        EquityPrice price = actual.get();
        
        assertThat(price.getSymbol()).isEqualTo(expected.getSymbol());
        assertThat(price.getIsin()).isEqualTo(expected.getIsin());
        assertThat(price.getOpen()).isEqualTo(expected.getOpen());
        assertThat(price.getHigh()).isEqualTo(expected.getHigh());
        assertThat(price.getLow()).isEqualTo(expected.getLow());
        assertThat(price.getClose()).isEqualTo(expected.getClose());
        assertThat(price.getVolume()).isEqualTo(expected.getVolume());
        assertThat(price.getExchange()).isEqualTo(expected.getExchange());
        assertThat(price.getCurrency()).isEqualTo(expected.getCurrency());
        assertThat(price.getTime()).isEqualTo(expected.getTime());
    }

    public static void assertEquityPriceList(List<EquityPrice> actual, List<EquityPrice> expected) {
        assertThat(actual).hasSameSizeAs(expected);
        
        for (int i = 0; i < expected.size(); i++) {
            EquityPrice expectedPrice = expected.get(i);
            EquityPrice actualPrice = actual.get(i);
            
            assertThat(actualPrice.getSymbol()).as("Symbol").isEqualTo(expectedPrice.getSymbol());
            assertThat(actualPrice.getIsin()).as("ISIN").isEqualTo(expectedPrice.getIsin());
            assertThat(actualPrice.getOpen()).as("Open price").isCloseTo(expectedPrice.getOpen(), within(0.001));
            assertThat(actualPrice.getHigh()).as("High price").isCloseTo(expectedPrice.getHigh(), within(0.001));
            assertThat(actualPrice.getLow()).as("Low price").isCloseTo(expectedPrice.getLow(), within(0.001));
            assertThat(actualPrice.getClose()).as("Close price").isCloseTo(expectedPrice.getClose(), within(0.001));
            assertThat(actualPrice.getVolume()).as("Volume").isEqualTo(expectedPrice.getVolume());
            assertThat(actualPrice.getExchange()).as("Exchange").isEqualTo(expectedPrice.getExchange());
            assertThat(actualPrice.getCurrency()).as("Currency").isEqualTo(expectedPrice.getCurrency());
            assertThat(actualPrice.getTime()).as("Time").isNotNull();
        }
    }

    public static void assertEquityPriceHistory(List<EquityPrice> actual, List<EquityPrice> expected) {
        assertThat(actual).hasSameSizeAs(expected);
        
        for (int i = 0; i < actual.size(); i++) {
            EquityPrice actualPrice = actual.get(i);
            EquityPrice expectedPrice = expected.get(i);
            
            assertThat(actualPrice.getSymbol()).isEqualTo(expectedPrice.getSymbol());
            assertThat(actualPrice.getClose()).isEqualTo(expectedPrice.getClose());
            assertThat(actualPrice.getTime()).isEqualTo(expectedPrice.getTime());
        }
    }

    public static void assertEquityPriceCloseValues(List<EquityPrice> prices, Double... expectedCloseValues) {
        assertThat(prices).hasSize(expectedCloseValues.length);
        assertThat(prices).extracting("close").containsExactly(expectedCloseValues);
    }

    public static void assertMarketIndexIndices(Optional<MarketIndexIndices> actual, MarketIndexIndices expected) {
        assertThat(actual).as("Market index indices").isPresent();
        MarketIndexIndices indices = actual.get();
        
        assertThat(indices.getKey()).as("Key").isEqualTo(expected.getKey());
        assertThat(indices.getIndex()).as("Index").isEqualTo(expected.getIndex());
        assertThat(indices.getIndexSymbol()).as("Index symbol").isEqualTo(expected.getIndexSymbol());
        assertThat(indices.getTimestamp()).as("Timestamp").isEqualTo(expected.getTimestamp());
        
        // Assert Market Data
        MarketData expectedMarketData = expected.getMarketData();
        MarketData actualMarketData = indices.getMarketData();
        assertThat(actualMarketData).as("Market data").isNotNull();
        assertThat(actualMarketData.getOpen()).as("Open price").isCloseTo(expectedMarketData.getOpen(), within(0.001));
        assertThat(actualMarketData.getHigh()).as("High price").isCloseTo(expectedMarketData.getHigh(), within(0.001));
        assertThat(actualMarketData.getLow()).as("Low price").isCloseTo(expectedMarketData.getLow(), within(0.001));
        assertThat(actualMarketData.getLast()).as("Close price").isCloseTo(expectedMarketData.getLast(), within(0.001));
        assertThat(actualMarketData.getPreviousClose()).as("Previous close").isCloseTo(expectedMarketData.getPreviousClose(), within(0.001));
        assertThat(actualMarketData.getPercentChange()).as("Percent change").isCloseTo(expectedMarketData.getPercentChange(), within(0.001));
        
        // Assert Fundamental Ratios
        FundamentalRatios expectedRatios = expected.getFundamentalRatios();
        FundamentalRatios actualRatios = indices.getFundamentalRatios();
        assertThat(actualRatios).as("Fundamental ratios").isNotNull();
        assertThat(actualRatios.getPriceToEarningRation()).as("PE ratio").isCloseTo(expectedRatios.getPriceToEarningRation(), within(0.001));
        assertThat(actualRatios.getPriceToBookRation()).as("PB ratio").isCloseTo(expectedRatios.getPriceToBookRation(), within(0.001));
        assertThat(actualRatios.getDividenYield()).as("Dividend yield").isCloseTo(expectedRatios.getDividenYield(), within(0.001));
        
        // Assert Market Breadth
        MarketBreadth expectedBreadth = expected.getMarketBreadth();
        MarketBreadth actualBreadth = indices.getMarketBreadth();
        assertThat(actualBreadth).as("Market breadth").isNotNull();
        assertThat(actualBreadth.getAdvances()).as("Advances").isEqualTo(expectedBreadth.getAdvances());
        assertThat(actualBreadth.getDeclines()).as("Declines").isEqualTo(expectedBreadth.getDeclines());
        assertThat(actualBreadth.getUnchanged()).as("Unchanged").isEqualTo(expectedBreadth.getUnchanged());
        
        // Assert Historical Comparison
        HistoricalComparison expectedHistory = expected.getHistoricalComparison();
        HistoricalComparison actualHistory = indices.getHistoricalComparison();
        assertThat(actualHistory).as("Historical comparison").isNotNull();
        assertThat(actualHistory.getValue()).as("Value").isCloseTo(expectedHistory.getValue(), within(0.001));
        assertThat(actualHistory.getPerChange365d()).as("365d change").isCloseTo(expectedHistory.getPerChange365d(), within(0.001));
        assertThat(actualHistory.getDate365dAgo()).as("365d ago date").isEqualTo(expectedHistory.getDate365dAgo());
        assertThat(actualHistory.getPerChange30d()).as("30d change").isCloseTo(expectedHistory.getPerChange30d(), within(0.001));
        assertThat(actualHistory.getDate30dAgo()).as("30d ago date").isEqualTo(expectedHistory.getDate30dAgo());
        assertThat(actualHistory.getPreviousDay()).as("Previous day").isCloseTo(expectedHistory.getPreviousDay(), within(0.001));
        assertThat(actualHistory.getOneWeekAgo()).as("One week ago").isCloseTo(expectedHistory.getOneWeekAgo(), within(0.001));
        assertThat(actualHistory.getOneMonthAgo()).as("One month ago").isCloseTo(expectedHistory.getOneMonthAgo(), within(0.001));
        assertThat(actualHistory.getOneYearAgo()).as("One year ago").isCloseTo(expectedHistory.getOneYearAgo(), within(0.001));
    }

    public static void assertMarketIndexIndicesList(List<MarketIndexIndices> actual, List<MarketIndexIndices> expected) {
        assertThat(actual).as("List size").hasSameSizeAs(expected);
        for (int i = 0; i < expected.size(); i++) {
            assertMarketIndexIndices(Optional.of(actual.get(i)), expected.get(i));
        }
    }
}
