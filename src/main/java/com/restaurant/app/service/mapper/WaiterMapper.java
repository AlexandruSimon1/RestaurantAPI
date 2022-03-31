package com.restaurant.app.service.mapper;

import com.restaurant.app.domain.Waiter;
import com.restaurant.app.service.dto.WaiterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Waiter} and its DTO {@link WaiterDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WaiterMapper extends EntityMapper<WaiterDTO, Waiter> {}
