/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Employee;
import com.elcom.vitalsign.model.dto.AccountPatientSenSorDTO;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class EmployeeCustomizeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeCustomizeRepository.class);

    private SessionFactory sessionFactory;

    @Autowired
    public EmployeeCustomizeRepository(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public Employee findByUuid(String uuid) {
        Session session = openSession();
        Object result = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM employee WHERE id = ?", Employee.class);
            query.setParameter(1, uuid);
            result = query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (Employee) result : null;
    }

    public Employee findByAccountId(String uuid) {
        Session session = openSession();
        Object result = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM employee WHERE account_id = ?", Employee.class);
            query.setParameter(1, uuid);
            result = query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (Employee) result : null;
    }

    public AccountPatientSenSorDTO findPatientSensor(String id) {
        Session session = openSession();
        Object result = null;
        try {
            Query query  = session.createNativeQuery("select d.serial_number as displayId, g.serial_number as gateId, p.patient_code as patientCode\n"
                    + ", GROUP_CONCAT(s.mac SEPARATOR '###') as sensorIdLst\n"
                    + "from patient p inner join employee_patient ep on p.id = ep.patient_id\n"
                    + "inner join sensor s on p.id = s.patient_id\n"
                    + "inner join gate g on g.serial_number = s.gate_id\n"
                    + "inner join display d on g.serial_number = d.gate_id\n"
                    + "where ep.employee_id = ?\n"
                    + "GROUP BY p.patient_code", AccountPatientSenSorDTO.class);
            query.setParameter(1, id);
            result = query.getSingleResult();

        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (AccountPatientSenSorDTO) result : null;
    }

    private Session openSession() {
        return this.sessionFactory.openSession();
    }

    private void closeSession(Session session) {
        if (session != null) {
            session.close();
        }
    }
}
