package com.example.sni.model;

import java.math.BigDecimal;

public class InsuranceType {

    private Integer id;
    private String name;
    private Integer duration;
    private BigDecimal price;

    public InsuranceType(int id, String name, Integer duration, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.duration=duration;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
