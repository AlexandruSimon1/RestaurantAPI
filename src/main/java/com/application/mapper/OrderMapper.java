package com.application.mapper;

import com.application.dto.OrderDTO;
import com.application.model.Order;
import org.mapstruct.Context;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

//Mapper is used in order to be able to export and import the information from data base and in data base
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toOrderDto(Order order, @Context NotificatorMappingContext context);

    @InheritInverseConfiguration
    Order fromOrderDto(OrderDTO orderDTO, @Context NotificatorMappingContext context);
}