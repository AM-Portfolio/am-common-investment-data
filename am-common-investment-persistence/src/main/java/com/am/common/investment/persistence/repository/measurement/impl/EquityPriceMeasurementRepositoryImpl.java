package com.am.common.investment.persistence.repository.measurement.impl;

import com.am.common.investment.persistence.influx.measurement.EquityPriceMeasurement;
import com.am.common.investment.persistence.repository.measurement.EquityPriceMeasurementRepository;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.dsl.Flux;
import com.influxdb.query.dsl.functions.restriction.Restrictions;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class EquityPriceMeasurementRepositoryImpl implements EquityPriceMeasurementRepository {

    private final InfluxDBClient influxDBClient;
    private final String bucket;

    public EquityPriceMeasurementRepositoryImpl(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
        this.bucket = "investment_data";
        System.out.println("Repository initialized with bucket: " + bucket);
    }

    @Override
    public void save(EquityPriceMeasurement measurement) {
        try (WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder().batchSize(1).build())) {
            Point point = Point.measurement("equity")
                .addTag("symbol", measurement.getSymbol())
                .addTag("isin", measurement.getIsin())
                .addTag("exchange", measurement.getExchange())
                .addField("open", measurement.getOpen())
                .addField("high", measurement.getHigh())
                .addField("low", measurement.getLow())
                .addField("close", measurement.getClose())
                .addField("currency", measurement.getCurrency())
                .addField("volume", measurement.getVolume())
                .time(measurement.getTime(), WritePrecision.NS);
            
            System.out.println("Writing point to InfluxDB:");
            System.out.println("  - Measurement: equity");
            System.out.println("  - Tags: symbol=" + measurement.getSymbol() + 
                ", isin=" + measurement.getIsin() + 
                ", exchange=" + measurement.getExchange());
            System.out.println("  - Fields: open=" + measurement.getOpen() + 
                ", high=" + measurement.getHigh() + 
                ", low=" + measurement.getLow() + 
                ", close=" + measurement.getClose() + 
                ", volume=" + measurement.getVolume() + 
                ", currency=" + measurement.getCurrency());
            System.out.println("  - Time: " + measurement.getTime());
            
            writeApi.writePoint(bucket, "org", point);
            writeApi.flush();
        }
    }

    @Override
    public Optional<EquityPriceMeasurement> findLatestByIsin(String isin) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -15m) " +
            "|> filter(fn: (r) => r._measurement == \"equity\") " +
            "|> filter(fn: (r) => r.isin == \"%s\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\") ",
            bucket, isin
        );

        System.out.println("Executing query: " + query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<EquityPriceMeasurement> results = queryApi.query(query, EquityPriceMeasurement.class);
        System.out.println("Query results: " + results);
        
        if (!results.isEmpty()) {
            EquityPriceMeasurement measurement = results.get(0);
            // Ensure all tags are set from the result
            measurement.setIsin(isin);
            measurement.setSymbol(results.get(0).getSymbol());
            measurement.setCurrency(results.get(0).getCurrency());
            measurement.setExchange(results.get(0).getExchange());
            return Optional.of(measurement);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<EquityPriceMeasurement> findLatestBySymbol(String symbol) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -15m) " +
            "|> filter(fn: (r) => r._measurement == \"equity\") " +
            "|> filter(fn: (r) => r.symbol == \"%s\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\") ",
            bucket, symbol
        );

        System.out.println("Executing InfluxDB query: " + query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<EquityPriceMeasurement> results = queryApi.query(query, EquityPriceMeasurement.class);
        System.out.println("Query results: " + results);
        
        if (!results.isEmpty()) {
            EquityPriceMeasurement measurement = results.get(0);
            // Set the tag values since they're not included in the pivot
            measurement.setSymbol(symbol);
            return Optional.of(measurement);
        }
        return Optional.empty();
    }

    @Override
    public List<EquityPriceMeasurement> findBySymbol(String symbol) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
            .filter(Restrictions.column("symbol").equal(symbol))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: %s, stop: %s) " +
            "|> filter(fn: (r) => r._measurement == \"equity\") " +
            "|> filter(fn: (r) => r.symbol == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\") ",
            bucket, startTime, endTime, symbol
        );

        System.out.println("Executing query: " + query);
        List<EquityPriceMeasurement> results = influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
        
        // Set tags since they're not included in pivot
        results.forEach(measurement -> {
            measurement.setSymbol(symbol);
        });
        
        return results;
    }

    @Override
    public List<EquityPriceMeasurement> findByIsin(String isin) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
            .filter(Restrictions.column("isin").equal(isin))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findByIsinAndTimeBetween(String isin, Instant startTime, Instant endTime) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: %s, stop: %s) " +
            "|> filter(fn: (r) => r._measurement == \"equity\") " +
            "|> filter(fn: (r) => r.isin == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\") ",
            bucket, startTime, endTime, isin
        );

        System.out.println("Executing query: " + query);
        List<EquityPriceMeasurement> results = influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
        
        // Set tags since they're not included in pivot
        results.forEach(measurement -> {
            measurement.setIsin(isin);
        });
        
        return results;
    }

    @Override
    public List<EquityPriceMeasurement> findByExchange(String exchange) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -24h) " +
            "|> filter(fn: (r) => r._measurement == \"equity\") " +
            "|> filter(fn: (r) => r.exchange == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\") ",
            bucket, exchange
        );

        System.out.println("Executing query: " + query);
        List<EquityPriceMeasurement> results = influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
        
        // Set tags since they're not included in pivot
        results.forEach(measurement -> {
            measurement.setExchange(exchange);
        });
        
        return results;
    }

    @Override
    public List<EquityPriceMeasurement> findByKeyAndTimeBetween(String key, Instant startTime, Instant endTime) {
        // Try by symbol first
        List<EquityPriceMeasurement> bySymbol = findBySymbolAndTimeBetween(key, startTime, endTime);
        if (!bySymbol.isEmpty()) {
            return bySymbol;
        }
        // If not found by symbol, try by ISIN
        return findByIsinAndTimeBetween(key, startTime, endTime);
    }
}
