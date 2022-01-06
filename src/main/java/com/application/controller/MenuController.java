package com.application.controller;

import com.application.dto.MenuDTO;
import com.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuDTO> getAllProducts() {
        return menuService.getAllProducts();
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDTO getProductById(@PathVariable int productId) {
        return menuService.getProductById(productId);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public MenuDTO deleteProductById(@PathVariable int productId) {
        return menuService.deleteProductById(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDTO createProduct(@RequestBody MenuDTO menuDTO) {
        return menuService.createProduct(menuDTO);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuDTO updateProduct(@PathVariable int productId, @RequestBody MenuDTO menuDTO) {
        return menuService.update(menuDTO, productId);
    }
}
