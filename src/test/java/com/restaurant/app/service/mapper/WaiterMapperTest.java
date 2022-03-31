package com.restaurant.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WaiterMapperTest {

    private WaiterMapper waiterMapper;

    @BeforeEach
    public void setUp() {
        waiterMapper = new WaiterMapperImpl();
    }
}
