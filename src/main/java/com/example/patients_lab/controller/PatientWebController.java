package com.example.patients_lab.controller;

import com.example.patients_lab.repository.PatientRepository;
import com.example.patients_lab.service.PatientService;
import com.example.patients_lab.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/patients")
public class PatientWebController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access_denied";
    }

    @GetMapping("/patients_list")
    public String showPatientsList(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients/patients_list";
    }

    @GetMapping("/create_patient")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String createPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/patient_form";
    }

    @PostMapping("/create_patient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients/patients_list";
    }

    @GetMapping("/edit_patient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String editPatient(@PathVariable("id") long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID нет: " + id));
        model.addAttribute("patient", patient);
        return "patients/patient_form";
    }

    @PostMapping("/edit_patient/{id}")
    public String updatePatient(@PathVariable("id") long id, @ModelAttribute("patient") Patient patient) {
        patient.setId(id);
        patientRepository.save(patient);
        return "redirect:/patients/patients_list";
    }

    @GetMapping("/delete_patient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public String deletePatient(@PathVariable("id") long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID нет: " + id));
        patientRepository.delete(patient);
        return "redirect:/patients/patients_list";
    }

}
