package com.am.common.investment.persistence.repository.measurement.impl;

import com.am.common.investment.persistence.influx.measurement.MarketIndexIndicesMeasurement;
import com.am.common.investment.persistence.repository.measurement.MarketIndexIndicesRepository;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApi;
import com.influxdb.client.WriteOptions;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MarketIndexIndicesRepositoryImpl implements MarketIndexIndicesRepository {
    private static final Logger logger = LoggerFactory.getLogger(MarketIndexIndicesRepositoryImpl.class);
    private static final String BUCKET_NAME = "investment_data";
    private static final String ORG = "org";
    private static final int BATCH_SIZE = 5000;
    private static final int FLUSH_INTERVAL = 1000; // milliseconds
    private static final String MEASUREMENT_NAME = "market_index";

    private final InfluxDBClient influxDBClient;

    @Override
    public void save(MarketIndexIndicesMeasurement measurement) {
        try (WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(1)
                .flushInterval(1000)
                .build())) {
            logger.debug("Writing measurement to InfluxDB: measurement={}, index={}, key={}, time={}", 
                MEASUREMENT_NAME, measurement.getIndex(), measurement.getKey(), measurement.getTime());
            writeApi.writeMeasurement(BUCKET_NAME, ORG, WritePrecision.NS, measurement);
            writeApi.flush();
            logger.debug("Successfully wrote measurement to InfluxDB");
        } catch (Exception e) {
            logger.error("Failed to write measurement to InfluxDB", e);
            throw e;
        }
    }

    @Override
    public void saveAll(List<MarketIndexIndicesMeasurement> measurements) {
        if (measurements == null || measurements.isEmpty()) {
            logger.warn("No measurements provided to save");
            return;
        }

        logger.debug("Starting batch save of {} market index measurements", measurements.size());
        try (WriteApi writeApi = influxDBClient.makeWriteApi(WriteOptions.builder()
                .batchSize(BATCH_SIZE)
                .flushInterval(FLUSH_INTERVAL)
                .build())) {
            writeApi.writeMeasurements(BUCKET_NAME, ORG, WritePrecision.NS, measurements);
            writeApi.flush();
            logger.debug("Successfully wrote {} measurements to InfluxDB", measurements.size());
        } catch (Exception e) {
            logger.error("Failed to write measurements to InfluxDB", e);
            throw e;
        }
    }

    @Override
    public Optional<MarketIndexIndicesMeasurement> findLatestByIndex(String index) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX, index
        );

        logger.debug("Executing findLatestByIndex query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for index: {}", results.size(), index);
        
        if (!results.isEmpty()) {
            MarketIndexIndicesMeasurement measurement = results.get(0);
            measurement.setIndex(index);
            return Optional.of(measurement);
        }
        return Optional.empty();
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByIndex(String index) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX, index
        );

        logger.debug("Executing findByIndex query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for index: {}", results.size(), index);
        
        results.forEach(measurement -> measurement.setIndex(index));
        return results;
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByIndexAndTimeBetween(String index, Instant startTime, Instant endTime) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: %s, stop: %s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, startTime.toString(), endTime.toString(), MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX, index
        );

        logger.debug("Executing findByIndexAndTimeBetween query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for index: {} between {} and {}", results.size(), index, startTime, endTime);
        
        results.forEach(measurement -> measurement.setIndex(index));
        return results;
    }

    @Override
    public Optional<MarketIndexIndicesMeasurement> findLatestByIndexSymbol(String indexSymbol) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX_SYMBOL, indexSymbol
        );

        logger.debug("Executing findLatestByIndexSymbol query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for indexSymbol: {}", results.size(), indexSymbol);
        
        if (!results.isEmpty()) {
            MarketIndexIndicesMeasurement measurement = results.get(0);
            measurement.setIndexSymbol(indexSymbol);
            return Optional.of(measurement);
        }
        return Optional.empty();
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByIndexSymbol(String indexSymbol) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX_SYMBOL, indexSymbol
        );

        logger.debug("Executing findByIndexSymbol query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for indexSymbol: {}", results.size(), indexSymbol);
        
        results.forEach(measurement -> measurement.setIndexSymbol(indexSymbol));
        return results;
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByIndexSymbolAndTimeBetween(String indexSymbol, Instant startTime, Instant endTime) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: %s, stop: %s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, startTime.toString(), endTime.toString(), MEASUREMENT_NAME, MarketIndexMeasurementTags.INDEX_SYMBOL, indexSymbol
        );

        logger.debug("Executing findByIndexSymbolAndTimeBetween query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for indexSymbol: {} between {} and {}", results.size(), indexSymbol, startTime, endTime);
        
        results.forEach(measurement -> measurement.setIndexSymbol(indexSymbol));
        return results;
    }

    @Override
    public Optional<MarketIndexIndicesMeasurement> findLatestByKey(String key) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> last() " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.KEY, key
        );

        logger.debug("Executing findLatestByKey query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for key: {}", results.size(), key);
        
        if (!results.isEmpty()) {
            MarketIndexIndicesMeasurement measurement = results.get(0);
            measurement.setKey(key);
            return Optional.of(measurement);
        }
        return Optional.empty();
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByKey(String key) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: -30d) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, MEASUREMENT_NAME, MarketIndexMeasurementTags.KEY, key
        );

        logger.debug("Executing findByKey query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for key: {}", results.size(), key);
        
        results.forEach(measurement -> measurement.setKey(key));
        return results;
    }

    @Override
    public List<MarketIndexIndicesMeasurement> findByKeyAndTimeRange(String key, Instant startTime, Instant endTime) {
        String query = String.format(
            "from(bucket: \"%s\") " +
            "|> range(start: %s, stop: %s) " +
            "|> filter(fn: (r) => r._measurement == \"%s\") " +
            "|> filter(fn: (r) => r[\"%s\"] == \"%s\") " +
            "|> pivot(rowKey: [\"_time\"], " +
            "        columnKey: [\"_field\"], " +
            "        valueColumn: \"_value\")",
            BUCKET_NAME, startTime.toString(), endTime.toString(), MEASUREMENT_NAME, MarketIndexMeasurementTags.KEY, key
        );

        logger.debug("Executing findByKeyAndTimeRange query: {}", query);
        QueryApi queryApi = influxDBClient.getQueryApi();
        List<MarketIndexIndicesMeasurement> results = queryApi.query(query, MarketIndexIndicesMeasurement.class);
        logger.debug("Found {} results for key: {} between {} and {}", results.size(), key, startTime, endTime);
        
        results.forEach(measurement -> measurement.setKey(key));
        return results;
    }
}
