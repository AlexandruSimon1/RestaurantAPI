package com.restaurant.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Order.
 */
@Entity
@javax.persistence.Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_number")
    private Integer orderNumber;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<Menu> menus = new HashSet<>();

    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    @OneToOne(mappedBy = "order")
    private Table table;

    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    @OneToOne(mappedBy = "order")
    private CheckOut checkOut;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return this.orderNumber;
    }

    public Order orderNumber(Integer orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(Set<Menu> menus) {
        if (this.menus != null) {
            this.menus.forEach(i -> i.setOrder(null));
        }
        if (menus != null) {
            menus.forEach(i -> i.setOrder(this));
        }
        this.menus = menus;
    }

    public Order menus(Set<Menu> menus) {
        this.setMenus(menus);
        return this;
    }

    public Order addMenu(Menu menu) {
        this.menus.add(menu);
        menu.setOrder(this);
        return this;
    }

    public Order removeMenu(Menu menu) {
        this.menus.remove(menu);
        menu.setOrder(null);
        return this;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        if (this.table != null) {
            this.table.setOrder(null);
        }
        if (table != null) {
            table.setOrder(this);
        }
        this.table = table;
    }

    public Order table(Table table) {
        this.setTable(table);
        return this;
    }

    public CheckOut getCheckOut() {
        return this.checkOut;
    }

    public void setCheckOut(CheckOut checkOut) {
        if (this.checkOut != null) {
            this.checkOut.setOrder(null);
        }
        if (checkOut != null) {
            checkOut.setOrder(this);
        }
        this.checkOut = checkOut;
    }

    public Order checkOut(CheckOut checkOut) {
        this.setCheckOut(checkOut);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderNumber=" + getOrderNumber() +
            "}";
    }
}
