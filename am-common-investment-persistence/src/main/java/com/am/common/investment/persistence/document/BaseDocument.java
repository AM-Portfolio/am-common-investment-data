package com.am.common.investment.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import com.am.common.investment.model.stockindice.AuditData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * MongoDB document for stock indices market data
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDocument {
    
    @Id
    private UUID id;
    @Indexed
    private String symbol;

    private String docVersion;
    private AuditData audit;
    private String source;
}
