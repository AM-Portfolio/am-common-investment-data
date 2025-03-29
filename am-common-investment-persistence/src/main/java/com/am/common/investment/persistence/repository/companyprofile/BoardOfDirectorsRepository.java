package com.am.common.investment.persistence.repository.companyprofile;

import org.springframework.stereotype.Repository;

import com.am.common.investment.persistence.document.companyprofile.BoardOfDirectorsDocument;
import com.am.common.investment.persistence.repository.financial.BaseDocumentRepository;

import java.util.UUID;

/**
 * Repository for accessing BoardOfDirectorsDocument in MongoDB
 */
@Repository
public interface BoardOfDirectorsRepository extends BaseDocumentRepository<BoardOfDirectorsDocument, UUID> {

}
