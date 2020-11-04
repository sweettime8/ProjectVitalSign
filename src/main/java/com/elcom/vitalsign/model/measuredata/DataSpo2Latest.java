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
@Table(name = "data_spo2_latest")
public class DataSpo2Latest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "gate_id")
    private String gateId;

    @Column(name = "sensor_id")
    private String sensorId;

    @Column(name = "spo2_v")
    private Integer spo2Value;

    @Column(name = "pi_v")
    private Integer piValue;

    @Column(name = "ppg_v")
    private Integer ppgValue;

    @Column(name = "pvi_v")
    private Integer pviValue;

    @Column(name = "pr")
    private Double pr;

    @Column(name = "measure_id")
    private String measureId;

    @Column(name = "step_id")
    private String stepId;

    @Column(name = "last_measure_at")
    private Timestamp lastMeasureAt;

    @Column(name = "last_updated_at")
    private Timestamp lastUpdatedAt;

    public DataSpo2Latest() {
    }

    public DataSpo2Latest(String gateId, String sensorId, Integer spo2Value, Integer piValue, Integer ppgValue,
             Integer pviValue, String measureId, String stepId, Timestamp lastMeasureAt) {
        this.gateId = gateId;
        this.sensorId = sensorId;
        this.spo2Value = spo2Value;
        this.piValue = piValue;
        this.ppgValue = ppgValue;
        this.pviValue = pviValue;
        this.measureId = measureId;
        this.stepId = stepId;
        this.lastMeasureAt = lastMeasureAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the gateId
     */
    public String getGateId() {
        return gateId;
    }

    /**
     * @param gateId the gateId to set
     */
    public void setGateId(String gateId) {
        this.gateId = gateId;
    }

    /**
     * @return the sensorId
     */
    public String getSensorId() {
        return sensorId;
    }

    /**
     * @param sensorId the sensorId to set
     */
    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * @return the measureId
     */
    public String getMeasureId() {
        return measureId;
    }

    /**
     * @param measureId the measureId to set
     */
    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    /**
     * @return the spo2Value
     */
    public Integer getSpo2Value() {
        return spo2Value;
    }

    /**
     * @param spo2Value the spo2Value to set
     */
    public void setSpo2Value(Integer spo2Value) {
        this.spo2Value = spo2Value;
    }

    /**
     * @return the piValue
     */
    public Integer getPiValue() {
        return piValue;
    }

    /**
     * @param piValue the piValue to set
     */
    public void setPiValue(Integer piValue) {
        this.piValue = piValue;
    }

    /**
     * @return the ppgValue
     */
    public Integer getPpgValue() {
        return ppgValue;
    }

    /**
     * @param ppgValue the ppgValue to set
     */
    public void setPpgValue(Integer ppgValue) {
        this.ppgValue = ppgValue;
    }

    /**
     * @return the pviValue
     */
    public Integer getPviValue() {
        return pviValue;
    }

    /**
     * @param pviValue the pviValue to set
     */
    public void setPviValue(Integer pviValue) {
        this.pviValue = pviValue;
    }

    /**
     * @return the lastMeasureAt
     */
    public Timestamp getLastMeasureAt() {
        return lastMeasureAt;
    }

    /**
     * @param lastMeasureAt the lastMeasureAt to set
     */
    public void setLastMeasureAt(Timestamp lastMeasureAt) {
        this.lastMeasureAt = lastMeasureAt;
    }

    /**
     * @return the lastUpdatedAt
     */
    public Timestamp getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    /**
     * @param lastUpdatedAt the lastUpdatedAt to set
     */
    public void setLastUpdatedAt(Timestamp lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    /**
     * @return the stepId
     */
    public String getStepId() {
        return stepId;
    }

    /**
     * @param stepId the stepId to set
     */
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    /**
     * @return the pr
     */
    public Double getPr() {
        return pr;
    }

    /**
     * @param pr the pr to set
     */
    public void setPr(Double pr) {
        this.pr = pr;
    }
}
