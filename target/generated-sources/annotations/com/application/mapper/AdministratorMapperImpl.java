package com.application.mapper;

import com.application.dto.AdminDTO;
import com.application.model.Administrator;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-20T10:29:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class AdministratorMapperImpl implements AdministratorMapper {

    @Override
    public AdminDTO toAdministratorDto(Administrator administrator, NotificatorMappingContext context) {
        AdminDTO target = context.getMappedInstance( administrator, AdminDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( administrator == null ) {
            return null;
        }

        AdminDTO adminDTO = new AdminDTO();

        context.storedMappedInstance( administrator, adminDTO );

        adminDTO.setId( administrator.getId() );
        adminDTO.setFirstName( administrator.getFirstName() );
        adminDTO.setLastName( administrator.getLastName() );
        adminDTO.setDateOfBirth( administrator.getDateOfBirth() );
        adminDTO.setAddress( administrator.getAddress() );
        adminDTO.setPhoneNumber( administrator.getPhoneNumber() );
        adminDTO.setEmail( administrator.getEmail() );

        return adminDTO;
    }

    @Override
    public Administrator fromAdministratorDto(AdminDTO adminDTO, NotificatorMappingContext context) {
        Administrator target = context.getMappedInstance( adminDTO, Administrator.class );
        if ( target != null ) {
            return target;
        }

        if ( adminDTO == null ) {
            return null;
        }

        Administrator administrator = new Administrator();

        context.storedMappedInstance( adminDTO, administrator );

        administrator.setId( adminDTO.getId() );
        administrator.setFirstName( adminDTO.getFirstName() );
        administrator.setLastName( adminDTO.getLastName() );
        administrator.setDateOfBirth( adminDTO.getDateOfBirth() );
        administrator.setAddress( adminDTO.getAddress() );
        administrator.setPhoneNumber( adminDTO.getPhoneNumber() );
        administrator.setEmail( adminDTO.getEmail() );

        return administrator;
    }
}
