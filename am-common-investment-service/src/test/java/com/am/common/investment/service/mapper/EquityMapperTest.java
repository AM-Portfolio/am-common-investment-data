package com.am.common.investment.service.mapper;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class EquityMapperTest {

    private EquityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new EquityMapper();
    }

    @Test
    void shouldMapMeasurementToModel() {
        // Given
        Instant now = Instant.now();
        EquityPriceMeasurement measurement = new EquityPriceMeasurement();
        measurement.setSymbol("AAPL");
        measurement.setIsin("US0378331005");
        measurement.setTime(now);
        measurement.setOpen(150.0);
        measurement.setHigh(155.0);
        measurement.setLow(149.0);
        measurement.setClose(152.0);
        measurement.setVolume(1000000L);
        measurement.setExchange("NASDAQ");
        measurement.setCurrency("USD");

        // When
        EquityPrice model = mapper.toModel(measurement);

        // Then
        assertThat(model).isNotNull();
        assertThat(model.getSymbol()).isEqualTo("AAPL");
        assertThat(model.getIsin()).isEqualTo("US0378331005");
        assertThat(model.getTime()).isEqualTo(now);
        assertThat(model.getOpen()).isEqualTo(150.0);
        assertThat(model.getHigh()).isEqualTo(155.0);
        assertThat(model.getLow()).isEqualTo(149.0);
        assertThat(model.getClose()).isEqualTo(152.0);
        assertThat(model.getVolume()).isEqualTo(1000000L);
        assertThat(model.getExchange()).isEqualTo("NASDAQ");
        assertThat(model.getCurrency()).isEqualTo("USD");
    }

    @Test
    void shouldMapModelToMeasurement() {
        // Given
        Instant now = Instant.now();
        EquityPrice model = new EquityPrice();
        model.setSymbol("GOOGL");
        model.setIsin("US02079K3059");
        model.setTime(now);
        model.setOpen(2800.0);
        model.setHigh(2850.0);
        model.setLow(2780.0);
        model.setClose(2820.0);
        model.setVolume(500000L);
        model.setExchange("NASDAQ");
        model.setCurrency("USD");

        // When
        EquityPriceMeasurement measurement = mapper.toMeasurement(model);

        // Then
        assertThat(measurement).isNotNull();
        assertThat(measurement.getSymbol()).isEqualTo("GOOGL");
        assertThat(measurement.getIsin()).isEqualTo("US02079K3059");
        assertThat(measurement.getTime()).isEqualTo(now);
        assertThat(measurement.getOpen()).isEqualTo(2800.0);
        assertThat(measurement.getHigh()).isEqualTo(2850.0);
        assertThat(measurement.getLow()).isEqualTo(2780.0);
        assertThat(measurement.getClose()).isEqualTo(2820.0);
        assertThat(measurement.getVolume()).isEqualTo(500000L);
        assertThat(measurement.getExchange()).isEqualTo("NASDAQ");
        assertThat(measurement.getCurrency()).isEqualTo("USD");
    }
}
