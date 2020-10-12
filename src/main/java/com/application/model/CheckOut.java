package com.application.model;


import com.google.common.base.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "checkout")
public class CheckOut implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String paymentType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false,name = "id", referencedColumnName = "id")
    private Order order;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckOut));
        CheckOut checkOut = (CheckOut) o;
        return Objects.equal(getId(), checkOut.getId()) &&
                Objects.equal(getOrder(), checkOut.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getOrder());
    }
}
