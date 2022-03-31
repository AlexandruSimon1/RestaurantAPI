package com.restaurant.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaiterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WaiterDTO.class);
        WaiterDTO waiterDTO1 = new WaiterDTO();
        waiterDTO1.setId(1L);
        WaiterDTO waiterDTO2 = new WaiterDTO();
        assertThat(waiterDTO1).isNotEqualTo(waiterDTO2);
        waiterDTO2.setId(waiterDTO1.getId());
        assertThat(waiterDTO1).isEqualTo(waiterDTO2);
        waiterDTO2.setId(2L);
        assertThat(waiterDTO1).isNotEqualTo(waiterDTO2);
        waiterDTO1.setId(null);
        assertThat(waiterDTO1).isNotEqualTo(waiterDTO2);
    }
}
