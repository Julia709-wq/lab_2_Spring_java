package com.example.patients_lab.controller;

import com.example.patients_lab.model.Patient;
import io.swagger.v3.oas.annotations.Operation;
import com.example.patients_lab.service.PatientService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/patients")
public class PatientRestController {
    @Autowired
    private PatientService patientService;

    @Operation(summary = "Получить всех пациентов")
    @ApiResponse(responseCode = "200", description = "Список пациентов получен")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Patient> getAllPatients() {return patientService.getAllPatients();}


    @Operation(summary = "Получить пациента по ID")
    @ApiResponse(responseCode = "200", description = "Пациент найден")
    @ApiResponse(responseCode = "404", description = "Пациент не найден")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> getPatientById(@PathVariable long id) {
        Patient patient = patientService.findById(id);
        return patient != null
                ? ResponseEntity.ok(patient)
                : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Добавить нового пациента")
    @ApiResponse(responseCode = "201", description = "Пациент успешно добавлен")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Patient saved = patientService.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @Operation(summary = "Обновить данные о пациенте")
    @ApiResponse(responseCode = "200", description = "Данные успешно обновлены")
    @ApiResponse(responseCode = "404", description = "Не удалось обновить данные")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> updatePatient(@PathVariable long id, @RequestBody Patient patient) {
        Patient current = patientService.findById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        patient.setId(id);
        return ResponseEntity.ok(patientService.save(patient));
    }


    @Operation(summary = "Удалить пациента")
    @ApiResponse(responseCode = "204", description = "Пациент удален")
    @ApiResponse(responseCode = "404", description = "Не удалось удалить пациента")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable long id) {
        Patient current = patientService.findById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
