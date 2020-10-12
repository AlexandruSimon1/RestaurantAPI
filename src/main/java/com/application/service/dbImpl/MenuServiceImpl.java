package com.application.service.dbImpl;

import com.application.dto.MenuDTO;
import com.application.exceptions.ApplicationException;
import com.application.exceptions.ExceptionType;
import com.application.mapper.MenuMapper;
import com.application.mapper.NotificatorMappingContext;
import com.application.model.Menu;
import com.application.repository.MenuRepository;
import com.application.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private final MenuRepository menuRepository;

    @Override
    public List<MenuDTO> getAllProducts() {
        return menuRepository.findAll().stream().
                map(menu -> MenuMapper.INSTANCE.toMenuDto(menu, new NotificatorMappingContext())).collect(Collectors.toList());
    }

    @Override
    public MenuDTO getProductById(int productId) {
        final Menu getProduct = menuRepository.findById(productId).
                orElseThrow(() -> new ApplicationException(ExceptionType.PRODUCT_NOT_FOUND));
        return MenuMapper.INSTANCE.toMenuDto(getProduct, new NotificatorMappingContext());
    }

    @Override
    public MenuDTO deleteProductById(int productId) {
        final Menu deleteProduct = menuRepository.findById(productId).
                orElseThrow(() -> new ApplicationException(ExceptionType.PRODUCT_NOT_FOUND));
        return MenuMapper.INSTANCE.toMenuDto(deleteProduct, new NotificatorMappingContext());
    }

    @Override
    public MenuDTO createProduct(MenuDTO menuDTO) {
        final Menu createProduct = MenuMapper.INSTANCE.fromMenuDto(menuDTO, new NotificatorMappingContext());
        final Menu saveProduct = menuRepository.save(createProduct);
        return MenuMapper.INSTANCE.toMenuDto(saveProduct, new NotificatorMappingContext());
    }

    @Override
    public MenuDTO update(MenuDTO menuDTO, int productId) {
        final Menu updateProduct = menuRepository.findById(productId).
                orElseThrow(() -> new ApplicationException(ExceptionType.PRODUCT_NOT_FOUND));
        updateProduct.setCategoryType(menuDTO.getCategoryType());
        updateProduct.setName(menuDTO.getName());
        updateProduct.setDescription(menuDTO.getDescription());
        updateProduct.setPrice(menuDTO.getPrice());
        menuRepository.save(updateProduct);
        return MenuMapper.INSTANCE.toMenuDto(updateProduct, new NotificatorMappingContext());
    }
}
