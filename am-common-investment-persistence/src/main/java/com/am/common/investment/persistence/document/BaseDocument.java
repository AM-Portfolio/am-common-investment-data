package com.am.common.investment.persistence.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import com.am.common.investment.model.stockindice.AuditData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

/**
 * Base document class for MongoDB entities
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDocument {
    
    @Id
    @Transient
    private UUID id;
    
    @Field("id")
    public void setId(UUID id) {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
    
    @Field("id")
    public UUID getId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
        return id;
    }
    
    @Indexed
    private String symbol;
    
    private String docVersion;
    private AuditData audit;
    private String source;
}
