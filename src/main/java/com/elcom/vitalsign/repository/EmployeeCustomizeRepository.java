/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Employee;
import com.elcom.vitalsign.model.dto.AccountPatientSenSorDTO;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
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

    public List<AccountPatientSenSorDTO> findPatientSensor(String id) {
        Session session = openSession();
        try {
            String sql = "select d.serial_number as displayId, g.serial_number as gateId, p.patient_code as patientCode,p.full_name as fullName\n"
                    + ",ROUND(DATEDIFF(CURDATE(), p.birth_date)/ 365, 0) as birthDate, p.gender as genDer\n"
                    + ", GROUP_CONCAT(s.mac SEPARATOR '###') as lstSensor\n"
                    + "from patient p inner join employee_patient ep on p.id = ep.patient_id\n"
                    + "inner join sensor s on p.id = s.patient_id\n"
                    + "inner join gate g on g.serial_number = s.gate_id\n"
                    + "inner join display d on g.serial_number = d.gate_id\n"
                    + "where ep.employee_id = :id\n"
                    + "GROUP BY p.patient_code;";

            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("id", id);

            query.addScalar("displayId", StandardBasicTypes.STRING);
            query.addScalar("gateId", StandardBasicTypes.STRING);
            query.addScalar("patientCode", StandardBasicTypes.STRING);
            query.addScalar("fullName", StandardBasicTypes.STRING);
            query.addScalar("birthDate", StandardBasicTypes.STRING);
            query.addScalar("genDer", StandardBasicTypes.STRING);
            query.addScalar("lstSensor", StandardBasicTypes.STRING);

            query.setResultTransformer(Transformers.aliasToBean(AccountPatientSenSorDTO.class));
            Object result = query.list();

            return result != null ? (List<AccountPatientSenSorDTO>) result : null;
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return null;
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
