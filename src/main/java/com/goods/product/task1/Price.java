package com.goods.product.task1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

import jakarta.persistence.*;

@Entity
@SequenceGenerator(name = "your_table_id_seq", sequenceName = "your_table_id_seq", allocationSize = 1)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "your_table_id_seq")
    private Long idbig;

    private Double valueprice;

    // Геттеры и сеттеры
    public Long getId() {
        return idbig;
    }

    public void setId(Long id) {
        this.idbig = id;
    }

    public Double getValue() {
        return valueprice;
    }

    public void setValue(Double value) {
        this.valueprice = value;
    }

}

