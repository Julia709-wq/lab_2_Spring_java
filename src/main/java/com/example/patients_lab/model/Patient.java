package com.example.patients_lab.model;

import jakarta.persistence.*;


@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private String birth_date;

    @Column(name = "diagnosis")
    private String diagnosis;

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getBirth_date() { return birth_date; }

    public void setBirth_date(String birth_date) { this.birth_date = birth_date; }

    public String getDiagnosis() { return diagnosis; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
}
