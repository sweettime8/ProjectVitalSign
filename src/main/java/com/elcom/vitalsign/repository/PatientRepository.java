/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.dto.PatientGateDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.elcom.vitalsign.model.dto.PatientWebDTO;

/**
 *
 * @author admin
 */
@Repository
public interface PatientRepository extends PagingAndSortingRepository<Patient, UUID> {

    @Query(value = " SELECT g.id AS gateId, g.activity_state as activityState "
            + " , d.name AS departmentName, r.name AS roomName, b.name AS bedName, p.patient_code AS patientCode, p.full_name AS patientName "
            + " , GROUP_CONCAT(e.full_name SEPARATOR '###') AS employeeName "
            + " FROM bed b "
            + " INNER JOIN patient p ON b.id = p.bed_id "
            + " LEFT JOIN employee_patient ep ON p.id = ep.patient_id "
            + " LEFT JOIN employee e ON e.id = ep.employee_id "
            + " LEFT JOIN gate g ON g.id = p.gate_id "
            + " INNER JOIN room r ON r.id = b.room_id "
            + " INNER JOIN department d ON d.id = r.department_id "
            + " WHERE p.status = 1 AND p.is_deleted = 0 GROUP BY p.full_name ", nativeQuery = true)
    List<PatientWebDTO> findAllForWeb();

    @Query(value = " SELECT g.id AS gateId, g.activity_state as activityState "
            + " , d.name AS departmentName, r.name AS roomName, b.name AS bedName, p.patient_code AS patientCode, p.full_name AS patientName "
            + " , GROUP_CONCAT(e.full_name SEPARATOR '###') AS employeeName "
            + " FROM bed b "
            + " INNER JOIN patient p ON b.id = p.bed_id "
            + " LEFT JOIN employee_patient ep ON p.id = ep.patient_id "
            + " LEFT JOIN employee e ON e.id = ep.employee_id "
            + " LEFT JOIN gate g ON g.id = p.gate_id "
            + " INNER JOIN room r ON r.id = b.room_id "
            + " INNER JOIN department d ON d.id = r.department_id "
            + " WHERE b.room_id = :roomId AND p.status = 1 AND p.is_deleted = 0 GROUP BY p.full_name ", nativeQuery = true)
    List<PatientWebDTO> findByRoomForWeb(@Param("roomId") String roomId);

    @Query(value = " select g.id AS gateId, p.patient_code AS patientCode"
            + " , p.full_name AS patientName, p.birth_date AS birthDate, p.mobile, p.additional_info AS additionalInfo "
            + " , d.name as departmentName, r.name as roomName, b.name as bedName "
            + " , GROUP_CONCAT(e.full_name SEPARATOR '###') as employeeName "
            + " from bed b "
            + " inner join patient p on b.id = p.bed_id "
            + " left join employee_patient ep on p.id = ep.patient_id "
            + " left join employee e on e.id = ep.employee_id "
            + " left join gate g on g.id = p.gate_id "
            + " inner join room r on r.id = b.room_id "
            + " inner join department d on d.id = r.department_id "
            + " where p.patient_code like :patientCode group by p.full_name ", nativeQuery = true)
    List<PatientGateDTO> findByPatientCodeForGate(@Param("patientCode") String patientCode);
}
