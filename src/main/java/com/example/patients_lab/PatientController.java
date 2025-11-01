package com.example.patients_lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/")
    String home(Model model) {
        return "home";
    }

    @GetMapping("patients/patients_list")
    public String showPatientsList(Model model) {
        model.addAttribute("patients", patientService.gettAllPatients());
        return "patients/patients_list";
    }

    @GetMapping("patients/create_patient")
    public String createPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patients/patient_form";
    }

    @PostMapping("patients/create_patient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients/patients_list";
    }

    @GetMapping("patients/edit_patient/{id}")
    public String editPatient(@PathVariable("id") long id, Model model) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID нет: " + id));
        model.addAttribute("patient", patient);
        return "patients/patient_form";
    }

    @PostMapping("patients/edit_patient/{id}")
    public String updatePatient(@PathVariable("id") long id, @ModelAttribute("patient") Patient patient) {
        patient.setId(id);
        patientRepository.save(patient);
        return "redirect:/patients/patients_list";
    }

    @GetMapping("patients/delete_patient/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID нет: " + id));
        patientRepository.delete(patient);
        return "redirect:/patients/patients_list";
    }

}
