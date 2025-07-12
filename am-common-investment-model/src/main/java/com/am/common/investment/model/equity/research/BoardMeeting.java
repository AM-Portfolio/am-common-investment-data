package com.am.common.investment.model.equity.research;

import java.time.LocalDate;

import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardMeeting {
    private String description;
    private LocalDate exDate;
}




