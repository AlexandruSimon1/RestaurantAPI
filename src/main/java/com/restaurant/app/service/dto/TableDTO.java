package com.restaurant.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.restaurant.app.domain.Table} entity.
 */
public class TableDTO implements Serializable {

    private Long id;

    private Integer number;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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
        if (!(o instanceof TableDTO)) {
            return false;
        }

        TableDTO tableDTO = (TableDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tableDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TableDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", order=" + getOrder() +
            "}";
    }
}
