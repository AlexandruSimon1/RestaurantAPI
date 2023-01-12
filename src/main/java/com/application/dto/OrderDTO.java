package com.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class OrderDTO {
    private Integer id;
    private Integer orderNumber;
    private List<MenuDTO> menus;
}
