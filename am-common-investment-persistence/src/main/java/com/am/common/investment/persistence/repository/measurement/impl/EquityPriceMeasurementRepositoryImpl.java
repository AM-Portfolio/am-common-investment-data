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
                .addTag("currency", measurement.getCurrency())
                .addField("open", measurement.getOpen())
                .addField("high", measurement.getHigh())
                .addField("low", measurement.getLow())
                .addField("close", measurement.getClose())
                .addField("volume", measurement.getVolume())
                .addField("exchange", measurement.getExchange())
                .time(measurement.getTime(), WritePrecision.NS);
            
            System.out.println("Writing point to InfluxDB:");
            System.out.println("  - Measurement: equity");
            System.out.println("  - Tags: symbol=" + measurement.getSymbol() + 
                ", isin=" + measurement.getIsin() + 
                ", currency=" + measurement.getCurrency());
            System.out.println("  - Fields: open=" + measurement.getOpen() + 
                ", high=" + measurement.getHigh() + 
                ", low=" + measurement.getLow() + 
                ", close=" + measurement.getClose() + 
                ", volume=" + measurement.getVolume() + 
                ", exchange=" + measurement.getExchange());
            System.out.println("  - Time: " + measurement.getTime());
            
            writeApi.writePoint(bucket, "org", point);
            writeApi.flush();
        }
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
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("symbol").equal(symbol))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public Optional<EquityPriceMeasurement> findLatestByIsin(String isin) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
            .filter(Restrictions.column("isin").equal(isin))
            .last()
            .toString();

        System.out.println("Executing query: " + query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<EquityPriceMeasurement> results = queryApi.query(query, EquityPriceMeasurement.class);
        System.out.println("Query results: " + results);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
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
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("isin").equal(isin))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findByExchange(String exchange) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(24 * 60 * 60))
            .filter(Restrictions.column("exchange").equal(exchange))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findByCurrency(String currency) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(24 * 60 * 60))
            .filter(Restrictions.column("currency").equal(currency))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findByVolumeGreaterThan(Long volume) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(24 * 60 * 60))
            .filter(Restrictions.column("volume").greater(volume))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findBySymbolAndCloseGreaterThan(String symbol, Double price) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
            .filter(Restrictions.and(
                Restrictions.column("symbol").equal(symbol),
                Restrictions.column("close").greater(price)
            ))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public List<EquityPriceMeasurement> findBySymbolAndCloseBetween(String symbol, Double minPrice, Double maxPrice) {
        String query = Flux.from(bucket)
            .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
            .filter(Restrictions.and(
                Restrictions.column("symbol").equal(symbol),
                Restrictions.column("close").greater(minPrice),
                Restrictions.column("close").less(maxPrice)
            ))
            .toString();

        System.out.println("Executing query: " + query);
        return influxDBClient.getQueryApi().query(query, EquityPriceMeasurement.class);
    }

    @Override
    public Double findHighestPriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("symbol").equal(symbol))
            .max("high")
            .toString();

        System.out.println("Executing query: " + query);
        List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
        System.out.println("Query results: " + results);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Double findLowestPriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("symbol").equal(symbol))
            .min("low")
            .toString();

        System.out.println("Executing query: " + query);
        List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
        System.out.println("Query results: " + results);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Double findAverageVolumeBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("symbol").equal(symbol))
            .mean("volume")
            .toString();

        System.out.println("Executing query: " + query);
        List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
        System.out.println("Query results: " + results);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Double findAverageClosePriceBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
        String query = Flux.from(bucket)
            .range(startTime, endTime)
            .filter(Restrictions.column("symbol").equal(symbol))
            .mean("close")
            .toString();

        System.out.println("Executing query: " + query);
        List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
        System.out.println("Query results: " + results);
        return results.isEmpty() ? null : results.get(0);
    }
}
