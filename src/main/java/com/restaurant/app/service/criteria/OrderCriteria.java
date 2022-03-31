package com.restaurant.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.restaurant.app.domain.Order} entity. This class is used
 * in {@link com.restaurant.app.web.rest.OrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter orderNumber;

    private LongFilter menuId;

    private LongFilter tableId;

    private LongFilter checkOutId;

    private Boolean distinct;

    public OrderCriteria() {}

    public OrderCriteria(OrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orderNumber = other.orderNumber == null ? null : other.orderNumber.copy();
        this.menuId = other.menuId == null ? null : other.menuId.copy();
        this.tableId = other.tableId == null ? null : other.tableId.copy();
        this.checkOutId = other.checkOutId == null ? null : other.checkOutId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrderCriteria copy() {
        return new OrderCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getOrderNumber() {
        return orderNumber;
    }

    public IntegerFilter orderNumber() {
        if (orderNumber == null) {
            orderNumber = new IntegerFilter();
        }
        return orderNumber;
    }

    public void setOrderNumber(IntegerFilter orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LongFilter getMenuId() {
        return menuId;
    }

    public LongFilter menuId() {
        if (menuId == null) {
            menuId = new LongFilter();
        }
        return menuId;
    }

    public void setMenuId(LongFilter menuId) {
        this.menuId = menuId;
    }

    public LongFilter getTableId() {
        return tableId;
    }

    public LongFilter tableId() {
        if (tableId == null) {
            tableId = new LongFilter();
        }
        return tableId;
    }

    public void setTableId(LongFilter tableId) {
        this.tableId = tableId;
    }

    public LongFilter getCheckOutId() {
        return checkOutId;
    }

    public LongFilter checkOutId() {
        if (checkOutId == null) {
            checkOutId = new LongFilter();
        }
        return checkOutId;
    }

    public void setCheckOutId(LongFilter checkOutId) {
        this.checkOutId = checkOutId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderCriteria that = (OrderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orderNumber, that.orderNumber) &&
            Objects.equals(menuId, that.menuId) &&
            Objects.equals(tableId, that.tableId) &&
            Objects.equals(checkOutId, that.checkOutId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, menuId, tableId, checkOutId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orderNumber != null ? "orderNumber=" + orderNumber + ", " : "") +
            (menuId != null ? "menuId=" + menuId + ", " : "") +
            (tableId != null ? "tableId=" + tableId + ", " : "") +
            (checkOutId != null ? "checkOutId=" + checkOutId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
