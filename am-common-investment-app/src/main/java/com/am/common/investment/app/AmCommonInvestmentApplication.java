package com.am.common.investment.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {
    "com.am.common.investment.service",
    "com.am.common.investment.persistence",
    "com.am.common.investment.app",
    "com.am.common.investment.model"
})
public class AmCommonInvestmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmCommonInvestmentApplication.class, args);
    }
}
