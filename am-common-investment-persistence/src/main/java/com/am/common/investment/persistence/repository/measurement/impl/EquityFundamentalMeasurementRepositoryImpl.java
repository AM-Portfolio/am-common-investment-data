// package com.am.common.investment.persistence.repository.measurement.impl;

// import com.am.common.investment.persistence.influx.measurement.EquityFundamentalMeasurement;
// import com.am.common.investment.persistence.repository.measurement.EquityFundamentalMeasurementRepository;
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
// public class EquityFundamentalMeasurementRepositoryImpl implements EquityFundamentalMeasurementRepository {

//     private final InfluxDBClient influxDBClient;
//     private final String bucket = "investment_data";

//     @Autowired
//     public EquityFundamentalMeasurementRepositoryImpl(InfluxDBClient influxDBClient) {
//         this.influxDBClient = influxDBClient;
//     }

//     @Override
//     public void save(EquityFundamentalMeasurement measurement) {
//         try (WriteApi writeApi = influxDBClient.getWriteApi()) {
//             writeApi.writeMeasurement(bucket, "default", measurement);
//         }
//     }

//     @Override
//     public void saveAll(Iterable<EquityFundamentalMeasurement> measurements) {
//         try (WriteApi writeApi = influxDBClient.getWriteApi()) {
//             writeApi.writeMeasurements(bucket, "default", measurements);
//         }
//     }

//     @Override
//     public Optional<EquityFundamentalMeasurement> findLatestBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .last()
//             .toString();

//         QueryApi queryApi = influxDBClient.getQueryApi();
//         List<EquityFundamentalMeasurement> results = queryApi.query(query, EquityFundamentalMeasurement.class);
//         return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public Optional<EquityFundamentalMeasurement> findLatestByIsin(String isin) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("isin").equal(isin))
//             .last()
//             .toString();

//         QueryApi queryApi = influxDBClient.getQueryApi();
//         List<EquityFundamentalMeasurement> results = queryApi.query(query, EquityFundamentalMeasurement.class);
//         return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findByIsin(String isin) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("isin").equal(isin))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findByIsinAndTimeBetween(String isin, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.column("isin").equal(isin))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findByMarketCapGreaterThan(Double marketCap) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
//             .filter(Restrictions.column("marketCap").greater(marketCap))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findByPeLessThan(Double pe) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
//             .filter(Restrictions.column("pe").less(pe))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public List<EquityFundamentalMeasurement> findByDividendYieldGreaterThan(Double dividendYield) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(7 * 24 * 60 * 60))
//             .filter(Restrictions.column("dividendYield").greater(dividendYield))
//             .toString();

//         return influxDBClient.getQueryApi().query(query, EquityFundamentalMeasurement.class);
//     }

//     @Override
//     public Double findAveragePeBySymbolAndTimeBetween(String symbol, Instant startTime, Instant endTime) {
//         String query = Flux.from(bucket)
//             .range(startTime, endTime)
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .mean("pe")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }

//     @Override
//     public Double findMaxDividendYieldBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .max("dividendYield")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }

//     @Override
//     public Double findMinPeBySymbol(String symbol) {
//         String query = Flux.from(bucket)
//             .range(Instant.now().minusSeconds(365 * 24 * 60 * 60))
//             .filter(Restrictions.column("symbol").equal(symbol))
//             .min("pe")
//             .toString();

//         List<Double> results = influxDBClient.getQueryApi().query(query, Double.class);
//         return results.isEmpty() ? null : results.get(0);
//     }
// }
