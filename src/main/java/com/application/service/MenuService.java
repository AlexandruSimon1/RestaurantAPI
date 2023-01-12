package com.application.service;

import com.application.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    List<MenuDTO> getAllProducts();

    MenuDTO getProductById(int productId);

    MenuDTO deleteProductById(int productId);

    MenuDTO createProduct(MenuDTO menuDTO);

    MenuDTO update(MenuDTO menuDTO, int productId);

}
