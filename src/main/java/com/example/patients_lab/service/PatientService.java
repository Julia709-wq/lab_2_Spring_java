package com.example.patients_lab.service;

import com.example.patients_lab.model.Patient;

import java.util.List;


public interface PatientService {

    List<Patient> getAllPatients();

    Patient save(Patient patient);

    Patient findById(Long id);

    void deleteById(Long id);
}
