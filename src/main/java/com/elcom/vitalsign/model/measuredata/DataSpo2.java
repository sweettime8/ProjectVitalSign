/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.measuredata;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "data_spo2")
public class DataSpo2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", length = 36, updatable = false, nullable = false)
    private String id;

    @Column(name = "gate_id")
    private String gateId;

    @Column(name = "display_id")
    private String displayId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "measure_id")
    private String measureId;

    @Column(name = "ts")
    private Float ts;

    @Column(name = "spo2")
    private Integer spo2;

    @Column(name = "pi")
    private Integer pi;

    @Column(name = "pr")
    private Double pr;

    @Column(name = "step")
    private int step;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @PrePersist
    void preInsert() {
        if (this.getId() == null) {
            this.setId(UUID.randomUUID().toString());
        }
        if (this.createdAt == null) {
            this.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
    }

    public DataSpo2() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGateId() {
        return gateId;
    }

    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMeasureId() {
        return measureId;
    }

    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    public Float getTs() {
        return ts;
    }

    public void setTs(Float ts) {
        this.ts = ts;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    public Integer getPi() {
        return pi;
    }

    public void setPi(Integer pi) {
        this.pi = pi;
    }

    public Double getPr() {
        return pr;
    }

    public void setPr(Double pr) {
        this.pr = pr;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

}
