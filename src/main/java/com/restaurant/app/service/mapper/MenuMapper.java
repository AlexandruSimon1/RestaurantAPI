package com.restaurant.app.service.mapper;

import com.restaurant.app.domain.Menu;
import com.restaurant.app.service.dto.MenuDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {
    @Mapping(target = "order", source = "order", qualifiedByName = "id")
    MenuDTO toDto(Menu s);
}
