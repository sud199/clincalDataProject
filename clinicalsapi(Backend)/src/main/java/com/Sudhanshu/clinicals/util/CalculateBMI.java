package com.Sudhanshu.clinicals.util;

import java.util.List;

import com.Sudhanshu.clinicals.model.ClinicalData;
import com.Sudhanshu.clinicals.model.Patient;

public class CalculateBMI {
	
	public static void calculateBMI(List<ClinicalData> clinicalData, ClinicalData eachEntry) {
		if (eachEntry.getComponentName().equals("hw")) {
			String[] heightAndWeight = eachEntry.getComponentValue().split("/");
			if (heightAndWeight != null && heightAndWeight.length > 1) {
				float feetToMetres = Float.parseFloat(heightAndWeight[0]) * 0.4536F;
				float BMI = Float.parseFloat(heightAndWeight[1]) / (feetToMetres * feetToMetres);
				ClinicalData bmiData = new ClinicalData();
				bmiData.setComponentName("BMI");
				bmiData.setComponentValue(Float.toString(BMI));
				clinicalData.add(bmiData);
			}
		}
	}

}
