package com.am.common.investment.persistence.document.companyprofile;

import com.am.common.investment.model.board.Director;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * MongoDB document for storing board of directors information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "board_of_directors")
public class BoardOfDirectorsDocument {
    
    @Id
    private UUID id;
    
    @Indexed
    private String companyId;
    
    private String companyName;
    
    private List<Director> directors;
    
    @LastModifiedDate
    private LocalDateTime lastUpdated;
    
    private LocalDate dataAsOf;
    
    private String source;
}
