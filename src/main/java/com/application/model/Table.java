package com.application.model;

import com.google.common.base.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
@javax.persistence.Table(name = "tables")
public class Table implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false,name = "id", referencedColumnName = "id")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        Table table = (Table) o;
        return Objects.equal(getId(), table.getId()) &&
                Objects.equal(getOrder(), table.getOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getOrder());
    }
}
