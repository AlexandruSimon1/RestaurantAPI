package com.application.mapper;

import com.application.dto.CheckOutDTO;
import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.model.CheckOut;
import com.application.model.Menu;
import com.application.model.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-20T10:29:17+0200",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 15.0.1 (Oracle Corporation)"
)
public class CheckOutMapperImpl implements CheckOutMapper {

    @Override
    public CheckOutDTO toCheckOutDto(CheckOut checkOut, NotificatorMappingContext context) {
        CheckOutDTO target = context.getMappedInstance( checkOut, CheckOutDTO.class );
        if ( target != null ) {
            return target;
        }

        if ( checkOut == null ) {
            return null;
        }

        CheckOutDTO checkOutDTO = new CheckOutDTO();

        context.storedMappedInstance( checkOut, checkOutDTO );

        checkOutDTO.setId( checkOut.getId() );
        checkOutDTO.setPaymentType( checkOut.getPaymentType() );
        checkOutDTO.setOrder( orderToOrderDTO( checkOut.getOrder(), context ) );

        return checkOutDTO;
    }

    @Override
    public CheckOut fromCheckOutDto(CheckOutDTO checkOutDTO, NotificatorMappingContext context) {
        CheckOut target = context.getMappedInstance( checkOutDTO, CheckOut.class );
        if ( target != null ) {
            return target;
        }

        if ( checkOutDTO == null ) {
            return null;
        }

        CheckOut checkOut = new CheckOut();

        context.storedMappedInstance( checkOutDTO, checkOut );

        checkOut.setId( checkOutDTO.getId() );
        checkOut.setPaymentType( checkOutDTO.getPaymentType() );
        checkOut.setOrder( orderDTOToOrder( checkOutDTO.getOrder(), context ) );

        return checkOut;
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
