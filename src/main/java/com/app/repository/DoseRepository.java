package com.app.repository;
import com.app.connectrion.DbStatus;
import com.app.model.Dose;
import com.app.model.Patient;
import com.app.model.Doctor;
import java.util.List;
import java.util.Optional;
public interface DoseRepository {
    DbStatus add(Dose dose);
    DbStatus update(Dose dose);
    DbStatus delete(Integer id);
    Dose findById(Integer id);
    List<Dose> findByPatient(Integer patientId);
    List<Dose> findByDoctor(Integer doctorId);
    List<Dose> findAll();
    List<Dose> findByPatientAndDoctor(Integer patientId,Integer doctorId);
}
