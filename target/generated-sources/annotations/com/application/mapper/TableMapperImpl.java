package com.application.mapper;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.dto.TableDTO;
import com.application.model.Menu;
import com.application.model.Order;
import com.application.model.Table;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-20T10:29:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class TableMapperImpl implements TableMapper {

    @Override
    public TableDTO toTableDto(Table table, NotificatorMappingContext context) {
        TableDTO target = context.getMappedInstance( table, TableDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( table == null ) {
            return null;
        }

        TableDTO tableDTO = new TableDTO();

        context.storedMappedInstance( table, tableDTO );

        tableDTO.setId( table.getId() );
        tableDTO.setNumber( table.getNumber() );
        tableDTO.setOrder( orderToOrderDTO( table.getOrder(), context ) );

        return tableDTO;
    }

    @Override
    public Table fromTableDto(TableDTO tableDTO, NotificatorMappingContext context) {
        Table target = context.getMappedInstance( tableDTO, Table.class );
        if ( target != null ) {
            return target;
        }

        if ( tableDTO == null ) {
            return null;
        }

        Table table = new Table();

        context.storedMappedInstance( tableDTO, table );

        table.setId( tableDTO.getId() );
        table.setNumber( tableDTO.getNumber() );
        table.setOrder( orderDTOToOrder( tableDTO.getOrder(), context ) );

        return table;
    }

    protected MenuDTO menuToMenuDTO(Menu menu, NotificatorMappingContext context) {
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

    protected List<MenuDTO> menuListToMenuDTOList(List<Menu> list, NotificatorMappingContext context) {
        List<MenuDTO> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<MenuDTO> list1 = new ArrayList<MenuDTO>( list.size() );
        context.storedMappedInstance( list, list1 );

        for ( Menu menu : list ) {
            list1.add( menuToMenuDTO( menu, context ) );
        }

        return list1;
    }

    protected OrderDTO orderToOrderDTO(Order order, NotificatorMappingContext context) {
        OrderDTO target = context.getMappedInstance( order, OrderDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( order == null ) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        context.storedMappedInstance( order, orderDTO );

        orderDTO.setId( order.getId() );
        orderDTO.setOrderNumber( order.getOrderNumber() );
        orderDTO.setMenus( menuListToMenuDTOList( order.getMenus(), context ) );

        return orderDTO;
    }

    protected Menu menuDTOToMenu(MenuDTO menuDTO, NotificatorMappingContext context) {
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

    protected List<Menu> menuDTOListToMenuList(List<MenuDTO> list, NotificatorMappingContext context) {
        List<Menu> target = context.getMappedInstance( list, List.class );
        if ( target != null ) {
            return target;
        }

        if ( list == null ) {
            return null;
        }

        List<Menu> list1 = new ArrayList<Menu>( list.size() );
        context.storedMappedInstance( list, list1 );

        for ( MenuDTO menuDTO : list ) {
            list1.add( menuDTOToMenu( menuDTO, context ) );
        }

        return list1;
    }

    protected Order orderDTOToOrder(OrderDTO orderDTO, NotificatorMappingContext context) {
        Order target = context.getMappedInstance( orderDTO, Order.class );
        if ( target != null ) {
            return target;
        }

        if ( orderDTO == null ) {
            return null;
        }

        Order order = new Order();

        context.storedMappedInstance( orderDTO, order );

        order.setId( orderDTO.getId() );
        order.setOrderNumber( orderDTO.getOrderNumber() );
        order.setMenus( menuDTOListToMenuList( orderDTO.getMenus(), context ) );

        return order;
    }
}
