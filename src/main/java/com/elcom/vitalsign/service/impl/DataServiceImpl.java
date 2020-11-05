/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.service.impl;

import com.elcom.vitalsign.model.measuredata.DataBp;
import com.elcom.vitalsign.model.measuredata.DataSpo2;
import com.elcom.vitalsign.model.measuredata.DataTemp;
import com.elcom.vitalsign.repository.DisplayRepository;
import com.elcom.vitalsign.repository.GateRepository;
import com.elcom.vitalsign.repository.measuredata.DataBpCustomizeRepository;
import com.elcom.vitalsign.repository.measuredata.DataBpRepository;
import com.elcom.vitalsign.repository.measuredata.DataPartition;
import com.elcom.vitalsign.repository.measuredata.DataSpo2CustomizeRepository;
import com.elcom.vitalsign.repository.measuredata.DataSpo2Repository;
import com.elcom.vitalsign.repository.measuredata.DataTempCustomizeRepository;
import com.elcom.vitalsign.service.DataService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elcom.vitalsign.repository.measuredata.DataTempRepository;

/**
 *
 * @author Admin
 */
@Service
public class DataServiceImpl implements DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    private final DataBpRepository dataBpRepository;
    private final DataBpCustomizeRepository dataBpCustomizeRepository;

    private final DataSpo2Repository dataSpo2Repository;
    private final DataSpo2CustomizeRepository dataSpo2CustomizeRepository;

    private final DataTempRepository dataTempRepository;
    private final DataTempCustomizeRepository dataTempCustomizeRepository;

    private final DataPartition dataPartition;
    private final DisplayRepository displayRepository;
    private final GateRepository gateRepository;


    /*private final DataRrRepository dataRrRepository;
    private final DataRrCustomizeRepository dataRrCustomizeRepository;
     */
    @Autowired
    public DataServiceImpl(
            DataBpRepository dataBpRepository, DataBpCustomizeRepository dataBpCustomizeRepository,
            DataSpo2Repository dataSpo2Repository, DataSpo2CustomizeRepository dataSpo2CustomizeRepository,
            DataTempRepository dataTempRepository, DataTempCustomizeRepository dataTempCustomizeRepository,
            DataPartition dataPartition, DisplayRepository displayRepository, GateRepository gateRepository
    ) {

        this.dataBpRepository = dataBpRepository;
        this.dataBpCustomizeRepository = dataBpCustomizeRepository;

        this.dataSpo2Repository = dataSpo2Repository;
        this.dataSpo2CustomizeRepository = dataSpo2CustomizeRepository;

        this.dataTempRepository = dataTempRepository;
        this.dataTempCustomizeRepository = dataTempCustomizeRepository;

        this.dataPartition = dataPartition;
        this.displayRepository = displayRepository;
        this.gateRepository = gateRepository;
    }

    @Override
    public void saveDataBp(List<DataBp> lst) {
        try {
            this.dataBpRepository.saveAll(lst);
            String gateIdLst = "";
            for (DataBp item : lst) {
                if (!gateIdLst.contains(item.getGateId())) {
                    gateIdLst += ",'" + item.getGateId() + "'";
                }
            }
            this.dataBpCustomizeRepository.progressDataLatest(lst.get(lst.size() - 1), gateIdLst.substring(1));
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }

    @Override
    public void saveDataSpo2(List<DataSpo2> lst) {
        try {
            this.dataSpo2Repository.saveAll(lst);
            String gateIdLst = "";
            for (DataSpo2 item : lst) {
                if (!gateIdLst.contains(item.getGateId())) {
                    gateIdLst += ",'" + item.getGateId() + "'";
                }
            }
            this.dataSpo2CustomizeRepository.progressDataLatest(lst.get(lst.size() - 1), gateIdLst.substring(1));
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }

    @Override
    public void saveDataTemp(List<DataTemp> lst) {
        try {
            this.dataTempRepository.saveAll(lst);
            String gateIdLst = "";
            for (DataTemp item : lst) {
                if (!gateIdLst.contains(item.getGateId())) {
                    gateIdLst += ",'" + item.getGateId() + "'";
                }
            }
            this.dataTempCustomizeRepository.progressDataLatest(lst.get(lst.size() - 1), gateIdLst.substring(1));
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
    }

    @Override
    public void addDataPartition(String[] tables) {
        this.dataPartition.addDataPartition(tables);
    }



//    @Override
//    public void updateDeviceInActive(String deviceType) {
//        if ("SENSOR".equals(deviceType)) {
//            this.dataEcgCustomizeRepository.updateSensorMeasureState();
//        } else if ("GATE".equals(deviceType)) {
//            this.dataEcgCustomizeRepository.updateGateActivityState();
//        }
//    }
//
    @Override
    public List<String> findAllGateForSubscribe() {
        try {
            return this.gateRepository.findAllGateForSubscribe();
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
        return null;
    }

    @Override
    public List<String> findAllDisplayForSubscribe() {
        try {
            return this.displayRepository.findAllDisplayForSubscribe();
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        }
        return null;
    }

    
}
