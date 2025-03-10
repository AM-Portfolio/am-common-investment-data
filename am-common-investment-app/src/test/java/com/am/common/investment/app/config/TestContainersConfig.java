package com.am.common.investment.app.config;

import static com.am.common.investment.app.constant.AppConstants.InfluxDB.*;

import com.am.common.investment.persistence.repository.measurement.EquityPriceMeasurementRepository;
import com.am.common.investment.persistence.repository.measurement.MarketIndexIndicesRepository;
import com.am.common.investment.persistence.repository.measurement.impl.EquityPriceMeasurementRepositoryImpl;
import com.am.common.investment.persistence.repository.measurement.impl.MarketIndexIndicesRepositoryImpl;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.InfluxDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@Configuration
@TestConfiguration
@Profile("test")
@ComponentScan(basePackages = {
    "com.am.common.investment.service",
    "com.am.common.investment.persistence",
    "com.am.common.investment.app"
})
public class TestContainersConfig implements AfterEachCallback {

    private static final String ADMIN_TOKEN = "my-super-secret-admin-token";
    private static final String ORG = "am_investment";
    private static final String BUCKET = "investment_data";

    @Container
    private static final InfluxDBContainer<?> influxDBContainer = new InfluxDBContainer<>(DockerImageName.parse("influxdb:2.7"))
        .withAuthEnabled(true)
        .withAdminToken(ADMIN_TOKEN)
        .withOrganization(ORG)
        .withBucket(BUCKET)
        .withExposedPorts(8086);

    static {
        influxDBContainer.start();
        System.setProperty("spring.influx.url", influxDBContainer.getUrl());
        System.setProperty("spring.influx.token", ADMIN_TOKEN);
        System.setProperty("spring.influx.org", ORG);
        System.setProperty("spring.influx.bucket", BUCKET);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public InfluxDBClient influxDBClient() {
        return InfluxDBClientFactory.create(
            influxDBContainer.getUrl(),
            ADMIN_TOKEN.toCharArray(),
            ORG,
            BUCKET
        );
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public EquityPriceMeasurementRepository equityPriceMeasurementRepository(InfluxDBClient influxDBClient) {
        return new EquityPriceMeasurementRepositoryImpl(influxDBClient);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public MarketIndexIndicesRepository marketIndexRepository(InfluxDBClient influxDBClient) {
        return new MarketIndexIndicesRepositoryImpl(influxDBClient);
    }

    @DynamicPropertySource
    static void registerInfluxDBProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.influx.url", influxDBContainer::getUrl);
        registry.add("spring.influx.token", () -> ADMIN_TOKEN);
        registry.add("spring.influx.org", () -> ORG);
        registry.add("spring.influx.bucket", () -> BUCKET);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        // Clean up all data after each test
        InfluxDBClient client = influxDBClient();
        client.getDeleteApi().delete(
            java.time.OffsetDateTime.of(2000, 1, 1, 0, 0, 0, 0, java.time.ZoneOffset.UTC),
            java.time.OffsetDateTime.now(java.time.ZoneOffset.UTC),
            String.format("_measurement=\"%s\"", MEASUREMENT_EQUITY_PRICE),
            BUCKET,
            ORG
        );
        System.out.println("Cleaned up all test data from InfluxDB");
    }
}
