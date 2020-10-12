package com.application.dto;

import lombok.Data;


@Data
public class TableDTO {
    private Integer id;
    private Integer number;
    private OrderDTO order;
}
