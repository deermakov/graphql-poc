package ru.lanit.research.graphql.domain;

import org.mapstruct.Mapper;

/**
 * Маппер доменных сущностей
 */
@Mapper(componentModel = "spring")
public interface EntityMapper {

    TargetEntity toTargetEntity(SourceEntity sourceEntity);
}
