package com.application.dto;

import com.application.model.enums.CategoryType;
import lombok.Data;

import java.util.Set;

@Data
public class MenuDTO {
    private Integer id;
    private CategoryType categoryType;
    private String name;
    private String description;
    private Integer price;
}
