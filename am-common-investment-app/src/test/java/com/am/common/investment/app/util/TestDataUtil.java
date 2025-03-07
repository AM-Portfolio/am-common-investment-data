package com.am.common.investment.app.util;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import java.time.Instant;

public class TestDataUtil {
    
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
}
