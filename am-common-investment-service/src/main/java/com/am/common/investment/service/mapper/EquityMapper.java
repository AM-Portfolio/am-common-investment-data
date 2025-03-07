package com.am.common.investment.service.mapper;

import com.am.common.investment.model.equity.EquityPrice;
import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import org.springframework.stereotype.Component;

@Component
public class EquityMapper {
    
    public EquityPrice toModel(EquityPriceMeasurement measurement) {
        if (measurement == null) {
            return null;
        }
        
        EquityPrice model = new EquityPrice();
        model.setSymbol(measurement.getSymbol());
        model.setIsin(measurement.getIsin());
        model.setTime(measurement.getTime());
        model.setOpen(measurement.getOpen());
        model.setHigh(measurement.getHigh());
        model.setLow(measurement.getLow());
        model.setClose(measurement.getClose());
        model.setVolume(measurement.getVolume());
        model.setExchange(measurement.getExchange());
        model.setCurrency(measurement.getCurrency());
        return model;
    }

    public EquityPriceMeasurement toMeasurement(EquityPrice model) {
        if (model == null) {
            return null;
        }
        
        EquityPriceMeasurement measurement = new EquityPriceMeasurement();
        measurement.setSymbol(model.getSymbol());
        measurement.setIsin(model.getIsin());
        measurement.setTime(model.getTime());
        measurement.setOpen(model.getOpen());
        measurement.setHigh(model.getHigh());
        measurement.setLow(model.getLow());
        measurement.setClose(model.getClose());
        measurement.setVolume(model.getVolume());
        measurement.setExchange(model.getExchange());
        measurement.setCurrency(model.getCurrency());
        return measurement;
    }
}
