package com.am.common.investment.model.equity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FundamentalRatios {
    private Double priceToEarningRation;
    private Double priceToBookRation;
    private Double dividenYield ;
}
