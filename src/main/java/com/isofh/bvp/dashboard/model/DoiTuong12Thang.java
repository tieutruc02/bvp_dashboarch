package com.isofh.bvp.dashboard.model;

import java.math.BigDecimal;
import java.util.Date;

public class DoiTuong12Thang {
    private Long id;
    private String name;
    private String type;
    private BigDecimal quantity;
    private Integer month;
    private Integer year;

    public DoiTuong12Thang(String type, BigDecimal quantity, Integer month, Integer year) {
        this.type = type;
        this.quantity = quantity;
        this.month = month;
        this.year = year;
    }

    public DoiTuong12Thang() {
    }

    public DoiTuong12Thang(Long id, String type, BigDecimal quantity, Integer month, Integer year) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.month = month;
        this.year = year;
    }

    public DoiTuong12Thang(Long id, String name, String type, BigDecimal quantity, Integer month, Integer year) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.month = month;
        this.year = year;
    }

    public DoiTuong12Thang(Long id, String name, String type, BigDecimal quantity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
