package com.app.service;

import com.app.model.*;

import java.util.List;


public interface MyService {
    void addPatient(Patient patient);
    void addDose(Dose dose);
    void addDoctor(Doctor doctor);
    void addEffect(Effect effect);

    void deletePatient(Integer id);
    void deleteDoctor(Integer id);
    void deleteDose(Integer id);
    void deleteEffect(Integer id);

    Patient findByIdPatient(Integer id);
    Doctor findByIdDoctor(Integer id);
    Effect findByIdEffect(Integer id);

    List<Patient> getAllPatient();
    List<Doctor> getAllDoctor();
    List<Dose> getAllDose();
    List<Effect> getAllEffect();

    List<Dose> getAllPatientDoses(Integer patientId);
    List<Effect> getAllPatientEffects(Integer patientId);

    List<Dose> getAllDoctorDoses(Integer doctorId);
    List<Effect> getAllDoctorEffects(Integer doctorId);

    List<Patient> getAllDoctorPatients(Integer doctorId);

    List<Dose> getAllDosesFromPatientAndDoctor(Integer patientId, Integer doctorId);
    List<Effect> getAllEffectsFromPatientAndDoctor(Integer patientId, Integer doctorId);



}
