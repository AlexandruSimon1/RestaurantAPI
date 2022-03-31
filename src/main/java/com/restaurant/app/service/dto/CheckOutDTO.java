package com.restaurant.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.app.domain.CheckOut} entity.
 */
public class CheckOutDTO implements Serializable {

    private Long id;

    private String paymentType;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckOutDTO)) {
            return false;
        }

        CheckOutDTO checkOutDTO = (CheckOutDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkOutDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckOutDTO{" +
            "id=" + getId() +
            ", paymentType='" + getPaymentType() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
