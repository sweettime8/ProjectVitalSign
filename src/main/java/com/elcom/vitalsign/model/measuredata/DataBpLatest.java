/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.model.measuredata;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
@Entity
@Table(name = "data_bp_latest")
public class DataBpLatest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @Column(name = "dia")
    private Long dia;

    @Column(name = "sys")
    private Long sys;

    @Column(name = "map")
    private Long map;

    @Column(name = "pr")
    private Integer pr;

    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    public DataBpLatest() {
    }

    public DataBpLatest(String gateId, String displayId, String sensorId, String measureId, Float ts,
            Long dia, Long sys, Long map, Integer pr) {
        this.gateId = gateId;
        this.displayId = displayId;
        this.sensorId = sensorId;
        this.measureId = measureId;
        this.ts = ts;
        this.dia = dia;
        this.sys = sys;
        this.map = map;
        this.pr = pr;
    }

    @PrePersist
    void preInsert() {
        if (this.lastUpdatedAt == null) {
            this.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getDia() {
        return dia;
    }

    public void setDia(Long dia) {
        this.dia = dia;
    }

    public Long getSys() {
        return sys;
    }

    public void setSys(Long sys) {
        this.sys = sys;
    }

    public Long getMap() {
        return map;
    }

    public void setMap(Long map) {
        this.map = map;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }

    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

}
