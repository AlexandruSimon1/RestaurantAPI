package com.application.service.dbImpl;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.model.Menu;
import com.application.model.Order;
import com.application.model.enums.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import com.application.repository.MenuRepository;
import com.application.repository.OrderRepository;
import com.application.repository.TableRepository;

import java.sql.ResultSet;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final int ID_VALUE = 1;
    private OrderServiceImpl orderService;
    private OrderDTO dto;
    private Order order;
    private List<Menu> menuList;
    private List<MenuDTO> menuDTOList;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private TableRepository tableRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderServiceImpl(orderRepository, menuRepository, tableRepository);

        order = new Order();
        order.setId(ID_VALUE);
        order.setOrderNumber(ID_VALUE);

        dto = new OrderDTO();
        dto.setId(ID_VALUE);
        dto.setOrderNumber(ID_VALUE);

        menuList = new ArrayList<>();
        Menu menu = new Menu();
        menu.setId(ID_VALUE);
        menu.setName("Pizza");
        menu.setCategoryType(CategoryType.PIZZA);
        menu.setPrice(95);
        menuList.add(menu);


        menuDTOList = new ArrayList<>();
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(ID_VALUE);
        menuDTO.setName("Pizza");
        menuDTO.setCategoryType(CategoryType.PIZZA);
        menuDTO.setPrice(95);
        menuDTOList.add(menuDTO);
    }

    @Test
    void getAllOrders() {
        //given
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        //when
        when(orderRepository.findAll()).thenReturn(orderList);
        List<OrderDTO> orderDTOList = orderService.getAllOrders();
        //then
        assertEquals(orderDTOList.size(), orderList.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderById() {
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        OrderDTO orderDTO = orderService.getOrderById(ID_VALUE);
        //then
        assertEquals(order.getId(), orderDTO.getId());
        verify(orderRepository, times(1)).findById(ID_VALUE);
    }

    @Test
    void deleteOrderById() {
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        orderRepository.deleteById(ID_VALUE);
        OrderDTO orderDTO = orderService.deleteOrderById(ID_VALUE);
        //then
        verify(orderRepository, times(1)).deleteById(ID_VALUE);
        assertEquals(order.getId(), orderDTO.getId());
    }

    @Test
    void updateOrder() {
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        OrderDTO updatedOrder = orderService.update(dto, ID_VALUE);
        //then
        assertNotNull(updatedOrder);
        assertEquals(ID_VALUE, updatedOrder.getId());
        assertEquals(dto.getOrderNumber(), updatedOrder.getOrderNumber());
    }

    @Test
    void createOrder() {
        //given
        OrderDTO orderDTOList = new OrderDTO();
        orderDTOList.setId(1);
        orderDTOList.setOrderNumber(1);

        Order createOrder = new Order();
        createOrder.setId(1);
        createOrder.setOrderNumber(1);
        //when
        when(orderRepository.save(createOrder)).thenReturn(createOrder);
        OrderDTO existingOrder = orderService.createOrder(orderDTOList);
        //then
        assertEquals(createOrder.getId(), existingOrder.getId());
        assertEquals(createOrder.getOrderNumber(), existingOrder.getOrderNumber());


    }

    @Test
    void findAllProductByOrderId() {
        //given
        order.setMenus(menuList);
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        menuDTOList = orderService.findAllProductByOrderId(ID_VALUE);
        //then
        assertFalse(menuDTOList.isEmpty());
        assertEquals(menuDTOList.size(), menuList.size());
    }

    @Test
    void updateProductByOrderId() {
        //given
        order.setMenus(menuList);

        Menu menu = new Menu();
        menu.setId(ID_VALUE);
        menu.setPrice(95);

        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(ID_VALUE);
        menuDTO.setPrice(95);
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        when(menuRepository.save(menu)).thenReturn(menu);
        MenuDTO updated = orderService.updateProductByOrderId(ID_VALUE, ID_VALUE, menuDTO);
        //then
        assertNotNull(updated);
        assertEquals(95, updated.getPrice());
    }

    @Test
    void deleteProductByOrderId() {
        //given
        List<Menu> menuListDelete = new ArrayList<>();
        Menu menu = new Menu();
        menu.setId(ID_VALUE);
        menu.setPrice(95);
        menuListDelete.add(menu);
        order.setMenus(menuListDelete);
        //when
        when(orderRepository.findById(ID_VALUE)).thenReturn(Optional.of(order));
        when(menuRepository.findById(ID_VALUE)).thenReturn(Optional.of(menu));
        MenuDTO deleted = orderService.deleteProductByOrderId(ID_VALUE, ID_VALUE);
        //then
        assertNotNull(deleted);
        assertEquals(ID_VALUE, deleted.getId());
    }
}
