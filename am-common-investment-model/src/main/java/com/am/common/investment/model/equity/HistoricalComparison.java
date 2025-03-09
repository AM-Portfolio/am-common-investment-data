package com.am.common.investment.model.equity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalComparison {
    private Double value;
    private Double perChange365d;
    private LocalDateTime date365dAgo;
    private Double perChange30d;
    private LocalDateTime date30dAgo;
    private Double previousDay;
    private Double oneWeekAgo;
    private Double oneMonthAgo;
    private Double oneYearAgo;
}
