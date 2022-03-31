package com.restaurant.app.service.mapper;

import com.restaurant.app.domain.CheckOut;
import com.restaurant.app.service.dto.CheckOutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CheckOut} and its DTO {@link CheckOutDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface CheckOutMapper extends EntityMapper<CheckOutDTO, CheckOut> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    CheckOutDTO toDto(CheckOut s);
}
