package com.app.service;

import com.app.model.*;
import com.app.repository.*;

import java.util.*;

public class MyServiceImpl implements MyService {

    private PatientRepository patientRepository = new PatientRepositoryImpl();
    private DoctorRepository doctorRepository = new DoctorRepositoryImpl();
    private DoseRepository doseRepository = new DoseRepositoryImpl();
    private EffectRepository effectRepository = new EffectRepositoryImpl();

    @Override
    public void addPatient(Patient patient) {
        patientRepository.add(Patient
                .builder()
                .name(patient.getName())
                .surname(patient.getSurname())
                .pesel(patient.getPesel())
                .placebo(patient.getPlacebo())
                .birthday(patient.getBirthday())
                .gender(patient.getGender())
                .doctorId(patient.getDoctorId())
                .build());
    }

    @Override
    public void addDose(Dose dose){
        doseRepository.add(Dose
                .builder()
                .doseValue(dose.getDoseValue())
                .dateDose(dose.getDateDose())
                .patientId(dose.getPatientId())
                .doctorId(dose.getDoctorId())
                .build());
    }

    @Override
    public void addDoctor(Doctor doctor){
       doctorRepository.add(Doctor
                .builder()
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .medicalLicence(doctor.getMedicalLicence())
                .phoneNumber(doctor.getPhoneNumber())
                .build());
    }

    @Override
    public void addEffect(Effect effect) {
        effectRepository.add(Effect
                .builder()
                .description(effect.getDescription())
                .dateEffect(effect.getDateEffect())
                .patientId(effect.getPatientId())
                .doctorId(effect.getDoctorId())
                .build());
    }


    @Override
    public void deletePatient(Integer id) {
        patientRepository.delete(id);
    }

    @Override
    public void deleteDoctor(Integer id) {
        doctorRepository.delete(id);
    }

    @Override
    public void deleteDose(Integer id) {
        doseRepository.delete(id);
    }

    @Override
    public void deleteEffect(Integer id) {
        effectRepository.delete(id);
    }

    @Override
    public Patient findByIdPatient(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public Doctor findByIdDoctor(Integer id) {
        return doctorRepository.findById(id);    }

    @Override
    public Effect findByIdEffect(Integer id) {
        return effectRepository.findById(id);
    }


    public List<Dose> getAllPatientDoses(Integer patientId){
        return doseRepository.findByPatient(patientId);
    }
    public List<Effect> getAllPatientEffects(Integer patientId){
        return effectRepository.findByPatient(patientId);
    }
    public List<Patient> getAllDoctorPatients(Integer doctorId){
        return patientRepository.findByDoctor(doctorId);
    }

    public List<Dose> getAllDoctorDoses(Integer doctorId){
        return doseRepository.findByDoctor(doctorId);
    }
    public List<Effect> getAllDoctorEffects(Integer doctorId){
        return effectRepository.findByDoctor(doctorId);
    }

    public List<Dose> getAllDosesFromPatientAndDoctor(Integer patientId, Integer doctorId){
        return doseRepository.findByPatientAndDoctor(patientId,doctorId);
    }
    public List<Effect> getAllEffectsFromPatientAndDoctor(Integer patientId, Integer doctorId){
        return effectRepository.findByPatientAndDoctor(patientId, doctorId);
    }

    @Override
    public List<Patient> getAllPatient() {
        return patientRepository.findAll();
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Dose> getAllDose() {
        return doseRepository.findAll();
    }

    @Override
    public List<Effect> getAllEffect() {
        return effectRepository.findAll();
    }

}
