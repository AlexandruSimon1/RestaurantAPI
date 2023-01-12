package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public OrderDTO deleteOrderById(@PathVariable int orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDTO updateOrderById(@PathVariable int orderId, @RequestBody OrderDTO orderDTO) {
        return orderService.update(orderDTO, orderId);
    }

    @GetMapping("/{orderId}/menus")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuDTO> getAllProductByOrderId(@PathVariable int orderId) {
        return orderService.findAllProductByOrderId(orderId);
    }

    @PutMapping("/{orderId}/menus/{removed_productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MenuDTO updateProductByOrderId(@PathVariable int orderId, @PathVariable int removed_productId, @RequestBody MenuDTO menuDTO) {
        return orderService.updateProductByOrderId(orderId, removed_productId, menuDTO);
    }

    @DeleteMapping("/{orderNumber}/menus/{removedProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MenuDTO deleteProductByOrderId(@PathVariable int orderNumber, @PathVariable int removedProductId) {
        return orderService.deleteProductByOrderId(orderNumber, removedProductId);
    }
}
