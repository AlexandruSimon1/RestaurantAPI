package com.application.mapper;

import com.application.dto.MenuDTO;
import com.application.model.Menu;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-20T10:29:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class MenuMapperImpl implements MenuMapper {

    @Override
    public MenuDTO toMenuDto(Menu menu, NotificatorMappingContext context) {
        MenuDTO target = context.getMappedInstance( menu, MenuDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( menu == null ) {
            return null;
        }

        MenuDTO menuDTO = new MenuDTO();

        context.storedMappedInstance( menu, menuDTO );

        menuDTO.setId( menu.getId() );
        menuDTO.setCategoryType( menu.getCategoryType() );
        menuDTO.setName( menu.getName() );
        menuDTO.setDescription( menu.getDescription() );
        menuDTO.setPrice( menu.getPrice() );

        return menuDTO;
    }

    @Override
    public Menu fromMenuDto(MenuDTO menuDTO, NotificatorMappingContext context) {
        Menu target = context.getMappedInstance( menuDTO, Menu.class );
        if ( target != null ) {
            return target;
        }

        if ( menuDTO == null ) {
            return null;
        }

        Menu menu = new Menu();

        context.storedMappedInstance( menuDTO, menu );

        menu.setId( menuDTO.getId() );
        menu.setCategoryType( menuDTO.getCategoryType() );
        menu.setName( menuDTO.getName() );
        menu.setDescription( menuDTO.getDescription() );
        menu.setPrice( menuDTO.getPrice() );

        return menu;
    }
}
