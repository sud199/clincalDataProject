package com.Sudhanshu.clinicals.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Sudhanshu.clinicals.Repos.PatientRepository;
import com.Sudhanshu.clinicals.model.ClinicalData;
import com.Sudhanshu.clinicals.model.Patient;
import com.Sudhanshu.clinicals.util.CalculateBMI;
@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {

	private PatientRepository patientRepository;
	HashMap<String, Integer> filters = new HashMap<>();

	@Autowired
	PatientController(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}
	
	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> getPatients() {
		return patientRepository.findAll();
	}

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		System.out.println(patient.getFirstName());
		return patientRepository.save(patient);
	}

	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public Patient getPatient(@PathVariable("id") int id) {
		return patientRepository.findById(id).get();
	}
	@RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
	public Patient analyse(@PathVariable("id") int id) {
		Patient patient = patientRepository.findById(id).get();
		List<ClinicalData> clinicalData = new ArrayList<>(patient.getClinicalData());
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		
		for (ClinicalData eachEntry : duplicateClinicalData) {
			if (filters.containsKey(eachEntry.getComponentName())){
				clinicalData.remove(eachEntry);
				continue;
			}
			else {
				filters.put(eachEntry.getComponentName(), null);
			}
			
			CalculateBMI.calculateBMI(clinicalData, eachEntry);
		}
		filters.clear();
		return patient;
	}

	
	
}
