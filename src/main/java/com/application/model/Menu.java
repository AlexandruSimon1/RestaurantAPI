package com.application.model;


import com.application.model.enums.CategoryType;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "menus")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CategoryType categoryType;
    private String name;
    private String description;
    private Integer price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(insertable = false, updatable = false, name = "id", referencedColumnName = "id")
    private Order order;
}