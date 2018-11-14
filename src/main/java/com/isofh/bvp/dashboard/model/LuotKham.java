package com.isofh.bvp.dashboard.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LuotKham {
    private String his_patienttype_id;
    private Date regdate;
    private BigDecimal soluong;


    public LuotKham(){}
    public LuotKham(String his_patienttype_id,Date regdate,BigDecimal soluong){
        this.his_patienttype_id=his_patienttype_id;
        this.regdate=regdate;
        this.soluong=soluong;
    }


    public String getHis_patienttype_id() {
        return his_patienttype_id;
    }

    public void setHis_patienttype_id(String his_patienttype_id) {
        this.his_patienttype_id = his_patienttype_id;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public BigDecimal getSoluong() {
        return soluong;
    }

    public void setSoluong(BigDecimal soluong) {
        this.soluong = soluong;
    }
}
