package com.example.patients_lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> gettAllPatients() {
        return patientRepository.findAll();
    }

    public Boolean isEmpty() {
        return patientRepository.count()==0;
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }
}
