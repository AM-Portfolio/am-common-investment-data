package com.am.common.investment.app.util;

import com.am.common.investment.model.equity.EquityPrice;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertEquals("List sizes should match", expected.size(), actual.size());
        
        for (int i = 0; i < expected.size(); i++) {
            EquityPrice expectedPrice = expected.get(i);
            EquityPrice actualPrice = actual.get(i);
            
            assertEquals("Symbol should match", expectedPrice.getSymbol(), actualPrice.getSymbol());
            assertEquals("ISIN should match", expectedPrice.getIsin(), actualPrice.getIsin());
            assertEquals("Open price should match", expectedPrice.getOpen(), actualPrice.getOpen(), 0.001);
            assertEquals("High price should match", expectedPrice.getHigh(), actualPrice.getHigh(), 0.001);
            assertEquals("Low price should match", expectedPrice.getLow(), actualPrice.getLow(), 0.001);
            assertEquals("Close price should match", expectedPrice.getClose(), actualPrice.getClose(), 0.001);
            assertEquals("Volume should match", expectedPrice.getVolume(), actualPrice.getVolume());
            assertEquals("Exchange should match", expectedPrice.getExchange(), actualPrice.getExchange());
            assertEquals("Currency should match", expectedPrice.getCurrency(), actualPrice.getCurrency());
            assertNotNull("Time should not be null", actualPrice.getTime());
        }
    }

    public static void assertEquityPriceHistory(List<EquityPrice> actual, List<EquityPrice> expected) {
        assertThat(actual).hasSize(expected.size());
        
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
}
