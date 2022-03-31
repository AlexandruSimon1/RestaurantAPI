package com.restaurant.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CheckOutDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckOutDTO.class);
        CheckOutDTO checkOutDTO1 = new CheckOutDTO();
        checkOutDTO1.setId(1L);
        CheckOutDTO checkOutDTO2 = new CheckOutDTO();
        assertThat(checkOutDTO1).isNotEqualTo(checkOutDTO2);
        checkOutDTO2.setId(checkOutDTO1.getId());
        assertThat(checkOutDTO1).isEqualTo(checkOutDTO2);
        checkOutDTO2.setId(2L);
        assertThat(checkOutDTO1).isNotEqualTo(checkOutDTO2);
        checkOutDTO1.setId(null);
        assertThat(checkOutDTO1).isNotEqualTo(checkOutDTO2);
    }
}
