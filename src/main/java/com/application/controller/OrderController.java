package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.dto.OrderDTO;
import com.application.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Api(value = "orders", description = "CRUD Operations for Orders", tags = "ORDERS")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL ORDERS", notes = "\n" + "This operation gets all orders")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ORDER BY ID", notes = "\n" + "This operation get order by id")
    public OrderDTO getOrderById(@PathVariable int orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE ORDER BY ID", notes = "\n" + "This operation deletes order by id")
    public OrderDTO deleteOrderById(@PathVariable int orderId) {
        return orderService.deleteOrderById(orderId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE ORDER", notes = "\n" + "This operation creates order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "UPDATE ORDER BY ID", notes = "\n" + "This operation updates an existing order")
    public OrderDTO updateOrderById(@PathVariable int orderId, @RequestBody OrderDTO orderDTO) {
        return orderService.update(orderDTO, orderId);
    }

    @GetMapping("/{orderId}/menus")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL PRODUCT BY ORDER NUMBER", notes = "\n" + "This operation gets all products from order number")
    public List<MenuDTO> getAllProductByOrderId(@PathVariable int orderId) {
        return orderService.findAllProductByOrderId(orderId);
    }

    @PutMapping("/{orderId}/menus/{removed_productId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "UPDATE PRODUCT BY ORDER NUMBER", notes = "\n" + "This operation updates products from existing order by order number")
    public MenuDTO updateProductByOrderId(@PathVariable int orderId, @PathVariable int removed_productId, @RequestBody MenuDTO menuDTO) {
        return orderService.updateProductByOrderId(orderId, removed_productId, menuDTO);
    }

    @DeleteMapping("/{orderNumber}/menus/{removedProductId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE PRODUCT BY ORDER NUMBER", notes = "\n" + "This operation deletes products from existing order by order number")
    public MenuDTO deleteProductByOrderId(@PathVariable int orderNumber, @PathVariable int removedProductId) {
        return orderService.deleteProductByOrderId(orderNumber, removedProductId);
    }
}
