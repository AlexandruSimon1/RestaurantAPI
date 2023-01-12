package com.application.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@JsonFormat
public enum CategoryType {
    PIZZA,
    SALAD,
    SOUP,
    DESSERT,
    BEVERAGE,
    ALCOHOL
}
