package com.Sudhanshu.clinicals.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Sudhanshu.clinicals.Repos.ClinicalDataRepository;
import com.Sudhanshu.clinicals.Repos.PatientRepository;
import com.Sudhanshu.clinicals.dto.ClinicalDataRequest;
import com.Sudhanshu.clinicals.model.ClinicalData;
import com.Sudhanshu.clinicals.model.Patient;
import com.Sudhanshu.clinicals.util.CalculateBMI;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	private ClinicalDataRepository clinicalDataRepository;
	private PatientRepository patientRepository;
	
	
	ClinicalDataController(ClinicalDataRepository clinicalDataRepository ,PatientRepository patientRepository ){
		this.clinicalDataRepository=clinicalDataRepository;
		this.patientRepository=patientRepository;
	}
	
	@RequestMapping(value="/clinicals" ,  method=RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest request) {
		Patient patient = patientRepository.findById(request.getPatientId()).get();
		ClinicalData clinicaldata=new ClinicalData();
		clinicaldata.setComponentName(request.getComponentName());
		clinicaldata.setComponentValue(request.getComponentValue());
		clinicaldata.setPatient(patient);
		return clinicalDataRepository.save(clinicaldata);
		
	}
	@RequestMapping(value = "/clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId ,
			@PathVariable("componentName") String componentName){
		if(componentName.equals("bmi")) {
			componentName="hw";
		}
		List<ClinicalData> clinicalData=clinicalDataRepository.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId,componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		
		for (ClinicalData eachEntry : duplicateClinicalData) {
			CalculateBMI.calculateBMI(clinicalData, eachEntry);
		}
		return clinicalData;
		
	}
}
