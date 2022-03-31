package com.restaurant.app.service.mapper;

import com.restaurant.app.domain.Table;
import com.restaurant.app.service.dto.TableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Table} and its DTO {@link TableDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface TableMapper extends EntityMapper<TableDTO, Table> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    TableDTO toDto(Table s);
}
