package com.am.common.investment.service.mapper;

import com.am.common.investment.model.equity.financial.BaseModel;
import com.am.common.investment.persistence.document.BaseDocument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BaseMapper {
    BaseMapper INSTANCE = Mappers.getMapper(BaseMapper.class);

    BaseModel toModel(BaseDocument document);

    BaseDocument toDocument(BaseModel model);
}
