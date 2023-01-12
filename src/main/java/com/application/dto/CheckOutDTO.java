package com.application.dto;

import lombok.Data;

@Data
public class CheckOutDTO {
    private Integer id;
    private String paymentType;
    private OrderDTO order;
}
