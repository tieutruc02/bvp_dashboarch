package com.isofh.bvp.dashboard.model;

import java.math.BigDecimal;
import java.util.Date;

public class DoiTuong {
    private Long id;
    private String name;
    private Date date;
    private BigDecimal value;

    public DoiTuong(){}

    public DoiTuong(Long id, String name, Date date, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
