package com.application.service.dbImpl;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.MenuMapper;
import com.application.mapper.NotificatorMappingContext;
import com.application.mapper.OrderMapper;
import com.application.model.Menu;
import com.application.model.Order;
import com.application.repository.MenuRepository;
import com.application.repository.OrderRepository;
import com.application.repository.TableRepository;
import com.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final MenuRepository menuRepository;
    @Autowired
    private final TableRepository tableRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().
        map(order -> OrderMapper.INSTANCE.toOrderDto(order, new NotificatorMappingContext())).
                        collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(int orderNumber) {
        final Order getOrder = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrderDto(getOrder, new NotificatorMappingContext());
    }

    @Override
    public OrderDTO deleteOrderById(int orderNumber) {
        final Order deleteOrder = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        return OrderMapper.INSTANCE.toOrderDto(deleteOrder, new NotificatorMappingContext());
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        final Order createOrder = OrderMapper.INSTANCE.fromOrderDto(orderDTO, new NotificatorMappingContext());
        final Order saveOrder = orderRepository.save(createOrder);
        return OrderMapper.INSTANCE.toOrderDto(saveOrder, new NotificatorMappingContext());
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO, int orderNumber) {
        final Order updateOrder = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        //updateOrder.addMenu(MenuMapper.INSTANCE.fromMenuDto(orderDTO.getMenus(),new NotificatorMappingContext()));
        orderRepository.save(updateOrder);
        return OrderMapper.INSTANCE.toOrderDto(updateOrder, new NotificatorMappingContext());
    }

    @Override
    public List<MenuDTO> findAllProductByOrderId(int orderNumber) {
        Order order = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        return order.getMenus().stream().
                map(menu -> MenuMapper.INSTANCE.toMenuDto(menu, new NotificatorMappingContext())).collect(Collectors.toList());
    }

    @Override
    public MenuDTO updateProductByOrderId(int orderNumber, int removed_productId, MenuDTO newMenuDTO) {
        Order order = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        Optional<Menu> deleteProduct = order.getMenus().stream().
                filter(menu -> Objects.equals(menu.getId(), removed_productId)).findFirst();
        if (!deleteProduct.isPresent()) {
            throw new ApplicationException(ExceptionType.PRODUCT_NOT_FOUND);
        }
        Menu updateProduct = deleteProduct.get();
        updateProduct.setCategoryType(newMenuDTO.getCategoryType());
        updateProduct.setName(newMenuDTO.getName());
        updateProduct.setDescription(newMenuDTO.getDescription());
        updateProduct.setPrice(newMenuDTO.getPrice());
        Menu saveProduct = menuRepository.save(updateProduct);
        return MenuMapper.INSTANCE.toMenuDto(saveProduct, new NotificatorMappingContext());
    }

    @Override
    public MenuDTO deleteProductByOrderId(int orderNumber, int removed_productId) {
        final Order order = orderRepository.findById(orderNumber).
                orElseThrow(() -> new ApplicationException(ExceptionType.ORDER_NOT_FOUND));
        final Menu deleteProduct = menuRepository.findById(removed_productId).
                orElseThrow(() -> new ApplicationException(ExceptionType.PRODUCT_NOT_FOUND));
        menuRepository.deleteById(removed_productId);
        order.getMenus().remove(deleteProduct);
        return MenuMapper.INSTANCE.toMenuDto(deleteProduct, new NotificatorMappingContext());
    }
}
