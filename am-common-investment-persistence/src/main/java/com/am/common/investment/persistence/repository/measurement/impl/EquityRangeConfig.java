package com.am.common.investment.persistence.repository.measurement.impl;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "investment.equity")
public class EquityRangeConfig {
    private String defaultRange = "-24h";
    private String historyRange = "-30d";
}
