package com.application.mapper;

import com.application.dto.WaiterDTO;
import com.application.model.Waiter;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WaiterMapper {
    WaiterMapper INSTANCE = Mappers.getMapper(WaiterMapper.class);

    WaiterDTO toWaiterDto(Waiter waiter, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Waiter fromWaiterDto(WaiterDTO waiterDTO, @Context NotificatorMappingContext context);
}