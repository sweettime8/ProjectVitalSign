/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elcom.vitalsign.repository;

import com.elcom.vitalsign.model.Patient;
import com.elcom.vitalsign.model.dto.PatientGateRelationDTO;
import com.elcom.vitalsign.model.dto.ReportDataBpDTO;
import com.elcom.vitalsign.model.dto.ReportDataSpo2DTO;
import com.elcom.vitalsign.model.dto.ReportDataTempDTO;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class PatientCustomizeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientCustomizeRepository.class);

    private SessionFactory sessionFactory;

    @Autowired
    public PatientCustomizeRepository(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }

        this.sessionFactory = factory.unwrap(SessionFactory.class);
    }

    public boolean patientGateRelation(PatientGateRelationDTO item) {
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createNativeQuery(" UPDATE patient SET gate_id = ? WHERE "
                    + (!item.isOverride() ? " gate_id IS NULL AND " : "") + " patient_code = ? ");
            query.setParameter(1, item.getGateId());
            query.setParameter(2, item.getPatientCode());
            int updateVal = query.executeUpdate();
            tx.commit();
            return updateVal >= 1;
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return false;
    }

    public boolean patientGateUnlink(PatientGateRelationDTO item) {
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();
            Query query = session.createNativeQuery(" UPDATE patient SET gate_id = null WHERE patient_code = ? AND gate_id = ? ");
            query.setParameter(1, item.getPatientCode());
            query.setParameter(2, item.getGateId());
            int updateVal = query.executeUpdate();
            tx.commit();
            return updateVal >= 1;
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return false;
    }

    public Patient findByUuid(String uuid) {
        Session session = openSession();
        Object result = null;
        try {
            Query query = session.createNativeQuery("SELECT * FROM patient WHERE id = ?", Patient.class);
            query.setParameter(1, uuid);
            result = query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return result != null ? (Patient) result : null;
    }

    public boolean isExistsPatientCode(String patientCode) {
        Session session = openSession();
        boolean result = true;
        try {
            Query query = session.createNativeQuery("SELECT id FROM patient WHERE patient_code = ?");
            query.setParameter(1, patientCode);
            query.getSingleResult();
        } catch (NoResultException ex) {
            result = false;
        } finally {
            closeSession(session);
        }
        return result;
    }

    public List<ReportDataBpDTO> findDataBp(String partitions, String patientId, String startTime, String endTime) {
        Session session = openSession();
        try {
            String strSql = " SELECT d.gate_id AS gateId, d.sensor_id as sensorId, d.sys_v AS sysValue, d.dia_v AS diaValue, d.map_v AS mapValue "
                    + " , d.measure_id AS measureId, d.step_id AS stepId, d.measure_at AS measureAt "
                    + " FROM patient p "
                    + " INNER JOIN data_bp PARTITION(" + partitions + ") d ON p.gate_id = d.gate_id "
                    + " WHERE p.id = :patientId AND d.measure_at BETWEEN :startTime AND :endTime"
                    + " ORDER BY d.measure_at desc ";
            NativeQuery query = session.createNativeQuery(strSql);

            query.setParameter("patientId", patientId);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            query.addScalar("gateId", StandardBasicTypes.STRING);
            query.addScalar("sensorId", StandardBasicTypes.STRING);
            query.addScalar("sysValue", StandardBasicTypes.INTEGER);
            query.addScalar("diaValue", StandardBasicTypes.INTEGER);
            query.addScalar("mapValue", StandardBasicTypes.INTEGER);
            query.addScalar("measureId", StandardBasicTypes.STRING);
            query.addScalar("stepId", StandardBasicTypes.STRING);
            query.addScalar("measureAt", StandardBasicTypes.TIMESTAMP);

            query.setResultTransformer(Transformers.aliasToBean(ReportDataBpDTO.class));
            Object result = query.list();

            return result != null ? (List<ReportDataBpDTO>) result : null;
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return null;
    }

    public List<ReportDataTempDTO> findDataTemp(String partitions, String patientId, String startTime, String endTime) {
        Session session = openSession();
        try {
            String strSql = " SELECT  d.gate_id AS gateId, d.sensor_id as sensorId, d.value, d.measure_id AS measureId, d.step_id AS stepId, d.measure_at AS measureAt "
                    + " FROM patient p "
                    + " INNER JOIN data_temp PARTITION(" + partitions + ") d ON p.gate_id = d.gate_id "
                    + " WHERE p.id = :patientId AND d.measure_at BETWEEN :startTime AND :endTime "
                    + " ORDER BY d.measure_at desc ";
            NativeQuery query = session.createNativeQuery(strSql);

            query.setParameter("patientId", patientId);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            query.addScalar("gateId", StandardBasicTypes.STRING);
            query.addScalar("sensorId", StandardBasicTypes.STRING);
            query.addScalar("value", StandardBasicTypes.DOUBLE);
            query.addScalar("measureId", StandardBasicTypes.STRING);
            query.addScalar("stepId", StandardBasicTypes.STRING);
            query.addScalar("measureAt", StandardBasicTypes.TIMESTAMP);

            query.setResultTransformer(Transformers.aliasToBean(ReportDataTempDTO.class));
            Object result = query.list();

            return result != null ? (List<ReportDataTempDTO>) result : null;
        } catch (Exception ex) {
            LOGGER.error(ex.toString());
        } finally {
            closeSession(session);
        }
        return null;
    }

    public List<ReportDataSpo2DTO> findDataSpo2(String partitions, String patientId, String startTime, String endTime) {
        Session session = openSession();
        try {
            String strSql = " SELECT  d.gate_id AS gateId, d.sensor_id as sensorId, d.spo2_v AS spo2Value, d.pi_v AS piValue, d.ppg_v AS ppgValue, d.pvi_v AS pviValue "
                    + " , d.measure_id AS measureId, d.step_id AS stepId, d.measure_at AS measureAt "
                    + " FROM patient p "
                    + " INNER JOIN data_spo2 PARTITION(" + partitions + ") d ON p.gate_id = d.gate_id "
                    + " WHERE p.id = :patientId AND d.measure_at BETWEEN :startTime AND :endTime "
                    + " ORDER BY d.measure_at desc ";
            NativeQuery query = session.createNativeQuery(strSql);

            query.setParameter("patientId", patientId);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);

            query.addScalar("gateId", StandardBasicTypes.STRING);
            query.addScalar("sensorId", StandardBasicTypes.STRING);
            query.addScalar("spo2Value", StandardBasicTypes.INTEGER);
            query.addScalar("piValue", StandardBasicTypes.INTEGER);
            query.addScalar("ppgValue", StandardBasicTypes.INTEGER);
            query.addScalar("pviValue", StandardBasicTypes.INTEGER);
            query.addScalar("measureId", StandardBasicTypes.STRING);
            query.addScalar("stepId", StandardBasicTypes.STRING);
            query.addScalar("measureAt", StandardBasicTypes.TIMESTAMP);

            query.setResultTransformer(Transformers.aliasToBean(ReportDataSpo2DTO.class));
            Object result = query.list();

            return result != null ? (List<ReportDataSpo2DTO>) result : null;
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
