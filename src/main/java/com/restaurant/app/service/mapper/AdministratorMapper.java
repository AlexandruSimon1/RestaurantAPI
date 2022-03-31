package com.restaurant.app.service.mapper;

import com.restaurant.app.domain.Administrator;
import com.restaurant.app.service.dto.AdministratorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administrator} and its DTO {@link AdministratorDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdministratorMapper extends EntityMapper<AdministratorDTO, Administrator> {}
