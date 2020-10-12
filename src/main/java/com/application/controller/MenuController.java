package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
@Api(value = "menu", description = "CRUD Operations for Menu", tags = "MENUS")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET ALL PRODUCTS", notes = "\n" + "This operation gets all products")
    public List<MenuDTO> getAllProducts() {
        return menuService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "GET PRODUCT BY ID", notes = "\n" + "This operation get product by id")
    public MenuDTO getProductById(@PathVariable int productId) {
        return menuService.getProductById(productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "DELETE PRODUCT BY ID", notes = "\n" + "This operation delete product by id")
    public MenuDTO deleteProductById(@PathVariable int productId) {
        return menuService.deleteProductById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "CREATE PRODUCT", notes = "\n" + "This operation creates a product")
    public MenuDTO createProduct(@RequestBody MenuDTO menuDTO) {
        return menuService.createProduct(menuDTO);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "UPDATE PRODUCT", notes = "\n" + "This operation updates a existing product")
    public MenuDTO updateProduct(@PathVariable int productId, @RequestBody MenuDTO menuDTO) {
        return menuService.update(menuDTO, productId);
    }
}
