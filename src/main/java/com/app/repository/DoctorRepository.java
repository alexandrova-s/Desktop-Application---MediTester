package com.app.repository;
import com.app.connectrion.DbStatus;
import com.app.model.Doctor;

import java.util.List;
import java.util.Optional;
public interface DoctorRepository {
    DbStatus add(Doctor doctor);
    DbStatus update(Doctor doctor);
    DbStatus delete(Integer id);
    Doctor findById(Integer id);
    List<Doctor> findAll();
    List<Integer> findAllId();
    boolean existsByPwz(String medicelLicence);
}
