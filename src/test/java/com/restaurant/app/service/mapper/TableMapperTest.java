package com.restaurant.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableMapperTest {

    private TableMapper tableMapper;

    @BeforeEach
    public void setUp() {
        tableMapper = new TableMapperImpl();
    }
}
