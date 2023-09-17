package com.Sudhanshu.clinicals.Repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sudhanshu.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
