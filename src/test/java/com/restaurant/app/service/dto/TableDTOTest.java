package com.restaurant.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableDTO.class);
        TableDTO tableDTO1 = new TableDTO();
        tableDTO1.setId(1L);
        TableDTO tableDTO2 = new TableDTO();
        assertThat(tableDTO1).isNotEqualTo(tableDTO2);
        tableDTO2.setId(tableDTO1.getId());
        assertThat(tableDTO1).isEqualTo(tableDTO2);
        tableDTO2.setId(2L);
        assertThat(tableDTO1).isNotEqualTo(tableDTO2);
        tableDTO1.setId(null);
        assertThat(tableDTO1).isNotEqualTo(tableDTO2);
    }
}
