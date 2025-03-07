package com.am.common.investment.persistence.influx.measurement;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

import java.time.Instant;

@Data
@Measurement(name = "equity_technicals")
public class EquityTechnicalMeasurement {
    @Column(tag = true)
    private String symbol;
    
    @Column(tag = true)
    private String isin;
    
    @Column(timestamp = true)
    private Instant time;
    
    // Moving Averages
    @Column
    private Double sma20;
    
    @Column
    private Double sma50;
    
    @Column
    private Double sma200;
    
    @Column
    private Double ema20;
    
    // Momentum Indicators
    @Column
    private Double rsi14;
    
    @Column
    private Double macd;
    
    @Column
    private Double macdSignal;
    
    @Column
    private Double macdHistogram;
    
    // Volatility Indicators
    @Column
    private Double bollingerUpper;
    
    @Column
    private Double bollingerMiddle;
    
    @Column
    private Double bollingerLower;
    
    @Column
    private Double atr14;
    
    // Volume Indicators
    @Column
    private Double obv;
    
    @Column
    private Double vwap;
}
