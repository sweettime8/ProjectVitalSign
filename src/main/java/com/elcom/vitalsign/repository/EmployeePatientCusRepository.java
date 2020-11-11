/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.EmployeePatient;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class EmployeePatientCusRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeePatientCusRepository.class);

    private SessionFactory sessionFactory;

    @Autowired
    public EmployeePatientCusRepository(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public List<EmployeePatient> findByEmployeeId(String uuid) {
        Session session = openSession();
        List<EmployeePatient> lst = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM employee_patient WHERE employee_id = ?", EmployeePatient.class);
            query.setParameter(1, uuid);
            lst = query.getResultList();
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return lst != null ? lst : null;
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
