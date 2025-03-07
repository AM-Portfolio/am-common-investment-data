package com.am.common.investment.model.equity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquityPrice {
    private String symbol;
    private String isin;
    private Instant time;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private String exchange;
    private String currency;
}
