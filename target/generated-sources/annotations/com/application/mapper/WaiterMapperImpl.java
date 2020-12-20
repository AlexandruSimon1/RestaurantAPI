package com.application.mapper;

import com.application.dto.WaiterDTO;
import com.application.model.Waiter;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-20T10:29:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class WaiterMapperImpl implements WaiterMapper {

    @Override
    public WaiterDTO toWaiterDto(Waiter waiter, NotificatorMappingContext context) {
        WaiterDTO target = context.getMappedInstance( waiter, WaiterDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( waiter == null ) {
            return null;
        }

        WaiterDTO waiterDTO = new WaiterDTO();

        context.storedMappedInstance( waiter, waiterDTO );

        waiterDTO.setId( waiter.getId() );
        waiterDTO.setFirstName( waiter.getFirstName() );
        waiterDTO.setLastName( waiter.getLastName() );
        waiterDTO.setDateOfBirth( waiter.getDateOfBirth() );
        waiterDTO.setAddress( waiter.getAddress() );
        waiterDTO.setPhoneNumber( waiter.getPhoneNumber() );
        waiterDTO.setEmail( waiter.getEmail() );

        return waiterDTO;
    }

    @Override
    public Waiter fromWaiterDto(WaiterDTO waiterDTO, NotificatorMappingContext context) {
        Waiter target = context.getMappedInstance( waiterDTO, Waiter.class );
        if ( target != null ) {
            return target;
        }

        if ( waiterDTO == null ) {
            return null;
        }

        Waiter waiter = new Waiter();

        context.storedMappedInstance( waiterDTO, waiter );

        waiter.setId( waiterDTO.getId() );
        waiter.setFirstName( waiterDTO.getFirstName() );
        waiter.setLastName( waiterDTO.getLastName() );
        waiter.setDateOfBirth( waiterDTO.getDateOfBirth() );
        waiter.setAddress( waiterDTO.getAddress() );
        waiter.setPhoneNumber( waiterDTO.getPhoneNumber() );
        waiter.setEmail( waiterDTO.getEmail() );

        return waiter;
    }
}
