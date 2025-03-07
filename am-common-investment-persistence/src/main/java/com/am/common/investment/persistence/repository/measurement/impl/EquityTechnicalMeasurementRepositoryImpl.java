// package com.am.common.investment.persistence.repository.measurement.impl;

// import com.am.common.investment.persistence.influx.measurement.EquityTechnicalMeasurement;
// import com.am.common.investment.persistence.repository.measurement.EquityTechnicalMeasurementRepository;
// import com.influxdb.client.InfluxDBClient;
// import com.influxdb.client.QueryApi;
// import com.influxdb.client.WriteApi;
// import com.influxdb.query.dsl.Flux;
// import com.influxdb.query.dsl.functions.restriction.Restrictions;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Repository;

// import java.time.Instant;
// import java.util.List;
// import java.util.Optional;

// @Repository
// public class EquityTechnicalMeasurementRepositoryImpl implements EquityTechnicalMeasurementRepository {

//     private final InfluxDBClient influxDBClient;
//     private final String bucket = "investment_data";

//     @Autowired
//     public EquityTechnicalMeasurementRepositoryImpl(InfluxDBClient influxDBClient) {
//         this.influxDBClient = influxDBClient;
//     }

//     @Override
//     public void save(EquityTechnicalMeasurement measurement) {
//         try (WriteApi writeApi = influxDBClient.getWriteApi()) {
//             writeApi.writeMeasurement(bucket, "default", measurement);
//         }
//     }

//     @Override
//     public void saveAll(Iterable<EquityTechnicalMeasurement> measurements) {
//         try (WriteApi writeApi = influxDBClient.getWriteApi()) {
//             writeApi.writeMeasurements(measurements);
//         }
//     }

//     @Override
//     public Optional<EquityTechnicalMeasurement> findLatestBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .last()
//             .toString();

//         QueryApi queryApi = influxDBClient.getQueryApi();
//         List<EquityTechnicalMeasurement> results = queryApi.query(query, EquityTechnicalMeasurement.class);
//         return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public Optional<EquityTechnicalMeasurement> findLatestByIsin(String isin) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("isin").equal(isin)
//             ))
//             .last()
//             .toString();

//         QueryApi queryApi = influxDBClient.getQueryApi();
//         List<EquityTechnicalMeasurement> results = queryApi.query(query, EquityTechnicalMeasurement.class);
//         return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findByIsin(String isin) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(30 * 24 * 60 * 60))
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("isin").equal(isin)
//             ))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findByIsinAndTimeBetween(String isin, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("isin").equal(isin)
//             ))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndRsi14LessThan(String symbol, Double rsi) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and r[\"rsi14\"] < %f)",
//             bucket, symbol, rsi
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndRsi14GreaterThan(String symbol, Double rsi) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and r[\"rsi14\"] > %f)",
//             bucket, symbol, rsi
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndMacdHistogramGreaterThan(String symbol, Double threshold) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and r[\"macdHistogram\"] > %f)",
//             bucket, symbol, threshold
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndSma20GreaterThanSma50(String symbol) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and r[\"sma20\"] > r[\"sma50\"])",
//             bucket, symbol
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndPriceAboveSma200(String symbol, Double currentPrice) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and %f > r[\"sma200\"])",
//             bucket, symbol, currentPrice
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndPriceNearBollingerLower(String symbol, Double currentPrice, Double threshold) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and math.abs(%f - r[\"bollingerLower\"]) <= %f)",
//             bucket, symbol, currentPrice, threshold
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public List<EquityTechnicalMeasurement> findBySymbolAndPriceNearBollingerUpper(String symbol, Double currentPrice, Double threshold) {
//         String query = String.format(
//             "from(bucket: \"%s\") " +
//             "|> range(start: -30d) " +
//             "|> filter(fn: (r) => r[\"_measurement\"] == \"equity_technical\" and r[\"symbol\"] == \"%s\" and math.abs(%f - r[\"bollingerUpper\"]) <= %f)",
//             bucket, symbol, currentPrice, threshold
//         );

//         return influxDBClient.getQueryApi().query(query, EquityTechnicalMeasurement.class);
//     }

//     @Override
//     public Double findAverageRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .mean("rsi14")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }

//     @Override
//     public Double findMaxRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .max("rsi14")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }

//     @Override
//     public Double findMinRsi14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .min("rsi14")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }

//     @Override
//     public Double findAverageAtr14BySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.and(
//                 Restrictions.measurement().equal("equity_technical"),
//                 Restrictions.tag("symbol").equal(symbol)
//             ))
//             .mean("atr14")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }
// }
