/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Sensor;
import com.elcom.vitalsign.model.dto.AccountPatientSenSorDTO;
import com.elcom.vitalsign.model.dto.GateWithSensor;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public class SensorCustomizeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorCustomizeRepository.class);

    private SessionFactory sessionFactory;

    @Autowired
    public SensorCustomizeRepository(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public Sensor findByUuid(String uuid) {
        Session session = openSession();
        try {
            return session.find(Sensor.class, uuid);
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return null;
    }

    public Sensor findByMac(String mac) {
        Session session = openSession();
        Object result = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM sensor WHERE mac = ?", Sensor.class);
            query.setParameter(1, mac);
            result = query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (Sensor) result : null;
    }

    public void sensorRelation(GateWithSensor gateWithSensor) {
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();
            gateWithSensor.getSensorLst().forEach((item) -> {
                try {
                    Query query = session.createNativeQuery(" SELECT id FROM sensor WHERE id = ? ");
                    query.setParameter(1, item.getId());
                    query.getSingleResult();

                    // Không throws NoResultException thì là tìm thấy sensor, cần update gateId cho nó
                    query = session.createNativeQuery(" UPDATE sensor SET gate_id = ? WHERE id = ? ");
                    query.setParameter(1, gateWithSensor.getGateId());
                    query.setParameter(2, item.getId());
                    query.executeUpdate();
                } catch (NoResultException ex) {
                    //Ko tìm thấy thì insert mới
                    LOGGER.error(ex.toString());
                    session.save(new Sensor(item.getId(), item.getName(), gateWithSensor.getGateId(), item.getPatientId(), item.getModel(),
                            item.getSensorType(), item.getMac(), item.getSerialNumber(), item.getManufacture(),
                            item.getFirmwareVersion(), item.getBatteryValue()));
                }
            });
            tx.commit();
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
    }

    public List<Sensor> findByGateId(String gateId) {
        Session session = openSession();
        List<Sensor> lstSensor = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM sensor WHERE gate_Id = ?", Sensor.class);
            query.setParameter(1, gateId);
            lstSensor = query.getResultList();
            return lstSensor;
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }

        return null;
    }

    public List<Sensor> findAllSensorByGateId(String gateId) {
        Session session = openSession();
        List<Sensor> lstSensor = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM sensor WHERE gate_Id = ?", Sensor.class);
            query.setParameter(1, gateId);
            lstSensor = query.getResultList();
            return lstSensor;
        } catch (NoResultException ex) {
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
