package com.restaurant.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.restaurant.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaiterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waiter.class);
        Waiter waiter1 = new Waiter();
        waiter1.setId(1L);
        Waiter waiter2 = new Waiter();
        waiter2.setId(waiter1.getId());
        assertThat(waiter1).isEqualTo(waiter2);
        waiter2.setId(2L);
        assertThat(waiter1).isNotEqualTo(waiter2);
        waiter1.setId(null);
        assertThat(waiter1).isNotEqualTo(waiter2);
    }
}
