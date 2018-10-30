package com.isofh.bvp.dashboard.web;

import javax.persistence.*;

@Entity
@Table(name = "HIS_VITIMES")
public class HIS_VITIMES {
    @Id
    @SequenceGenerator(name="HIS_VITIMES_SEQ", sequenceName="HIS_VITIMES_SEQ",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HIS_VITIMES_SEQ")
    @Column(name = "HIS_VITIMES_ID", unique = true, nullable = false)
    private Long his_vitimes_id;
    @Column(name = "PATIENTVALUE")
    private String patientvalue;
    @Column(name = "HIS_PATIENTHISTORY_ID")
    private Integer his_patienthistory_id;
    @Column(name = "TYPE_OBJECT")
    private String type_object;

    public Long getHis_vitimes_id() {
        return his_vitimes_id;
    }

    public void setHis_vitimes_id(Long his_vitimes_id) {
        this.his_vitimes_id = his_vitimes_id;
    }

    public String getPatientvalue() {
        return patientvalue;
    }

    public void setPatientvalue(String patientvalue) {
        this.patientvalue = patientvalue;
    }

    public Integer getHis_patienthistory_id() {
        return his_patienthistory_id;
    }

    public void setHis_patienthistory_id(Integer his_patienthistory_id) {
        this.his_patienthistory_id = his_patienthistory_id;
    }

    public String getType_object() {
        return type_object;
    }

    public void setType_object(String type_object) {
        this.type_object = type_object;
    }
}
