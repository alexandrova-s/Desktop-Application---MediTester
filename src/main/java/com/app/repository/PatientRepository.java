package com.app.repository;

import com.app.connectrion.DbStatus;
import com.app.model.Patient;

import java.util.List;

public interface PatientRepository {
    DbStatus add(Patient patient);
    DbStatus update(Patient patient);
    DbStatus delete(Integer id);
    Patient findById(Integer id);
    List<Patient> findByDoctor(Integer doctorId);
    List<Patient> findAll();boolean existsByPesel(String pesel);


}
