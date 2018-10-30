package com.isofh.bvp.dashboard.model;

import java.math.BigDecimal;
import java.util.List;

public class BieuDo {
    private List<String> names;
    private List<BigDecimal> values;

    public BieuDo() {
    }

    public BieuDo(List<String> names, List<BigDecimal> values) {
        this.names = names;
        this.values = values;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<BigDecimal> getValues() {
        return values;
    }

    public void setValues(List<BigDecimal> values) {
        this.values = values;
    }
}
