package com.application.mapper;

import com.application.dto.CheckOutDTO;
import com.application.model.CheckOut;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CheckOutMapper {
    CheckOutMapper INSTANCE = Mappers.getMapper(CheckOutMapper.class);

    CheckOutDTO toCheckOutDto(CheckOut checkOut, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    CheckOut fromCheckOutDto(CheckOutDTO checkOutDTO, @Context NotificatorMappingContext context);
}