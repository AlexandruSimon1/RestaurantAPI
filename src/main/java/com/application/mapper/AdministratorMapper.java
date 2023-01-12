package com.application.mapper;

import com.application.dto.AdminDTO;
import com.application.model.Administrator;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdministratorMapper {
    AdministratorMapper INSTANCE = Mappers.getMapper(AdministratorMapper.class);

    AdminDTO toAdministratorDto(Administrator administrator, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Administrator fromAdministratorDto(AdminDTO adminDTO, @Context NotificatorMappingContext context);
}
