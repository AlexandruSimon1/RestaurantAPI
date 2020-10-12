package com.application.model;


import com.google.common.base.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Data
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orderNumber;

    @OneToOne(mappedBy = "order")
    private CheckOut checkOut;

    @OneToMany(mappedBy ="order" ,cascade = CascadeType.ALL)
    private List<Menu> menus;

    @OneToOne(mappedBy = "order")
    private com.application.model.Table table;

    public void addMenu(Menu menu) {
        if (menus == null) {
            menus = new ArrayList<>();
        }
        menus.add(menu);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equal(getId(), order.getId()) &&
                Objects.equal(getMenus(), order.getMenus());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getMenus());
    }
}
