package com.app.repository;
import com.app.connectrion.DbStatus;
import com.app.model.Effect;
import com.app.model.Patient;
import com.app.model.Doctor;
import java.util.List;
import java.util.Optional;
public interface EffectRepository {
    DbStatus add(Effect effect);
    DbStatus update(Effect effect);
    DbStatus delete(Integer id);
    Effect findById(Integer id);
    List<Effect> findByPatient(Integer patientId);
    List<Effect> findByDoctor(Integer doctorId);
    List<Effect> findAll();
    List<Effect> findByPatientAndDoctor(Integer patientId,Integer doctorId);

}
