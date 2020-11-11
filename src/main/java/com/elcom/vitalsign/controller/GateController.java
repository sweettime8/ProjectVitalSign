package com.elcom.vitalsign.controller;

import com.elcom.vitalsign.model.Account;
import com.elcom.vitalsign.model.Employee;
import com.elcom.vitalsign.model.EmployeePatient;
import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.dto.AccountPatientSenSorDTO;
import com.elcom.vitalsign.model.dto.ResponseData;
import com.elcom.vitalsign.repository.EmployeeCustomizeRepository;
import com.elcom.vitalsign.repository.EmployeePatientCusRepository;
import com.elcom.vitalsign.repository.SensorCustomizeRepository;
import com.elcom.vitalsign.service.DataService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 *
 * @author anhdv
 */
@RestController
@RequestMapping("/gate")
public class GateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GateController.class);

    @Autowired
    private final DataService dataService;

    @Autowired
    private final EmployeeCustomizeRepository employeeCustomizeRepository;

    @Autowired
    private final EmployeePatientCusRepository employeePatientCusRepository;

    @Autowired
    private final SensorCustomizeRepository sensorCustomizeRepository;

    public GateController(DataService dataService, EmployeeCustomizeRepository employeeCustomizeRepository,
            EmployeePatientCusRepository employeePatientCusRepository, SensorCustomizeRepository sensorCustomizeRepository
    ) {
        this.dataService = dataService;
        this.employeeCustomizeRepository = employeeCustomizeRepository;
        this.employeePatientCusRepository = employeePatientCusRepository;
        this.sensorCustomizeRepository = sensorCustomizeRepository;
    }

    @RequestMapping(value = "/api/account/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable String id) {
        id = "47f28f75-9f8a-47fb-b858-f9d64c466f2b";
        AccountPatientSenSorDTO accountPatientSenSorDTO =  employeeCustomizeRepository.findPatientSensor(id);
        return new ResponseEntity<>(accountPatientSenSorDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/api/account", method = RequestMethod.GET)
    public ResponseEntity<String> getAllUser() {
        
        return new ResponseEntity<>("mrd", HttpStatus.CREATED);
    }

}
