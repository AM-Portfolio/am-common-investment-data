package com.am.common.investment.model.equity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketBreadth {
    private String declines;
    private String advances;
    private String unchanged;
}
