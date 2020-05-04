package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleStringProperty;

public class Test_Model {
	
	private SimpleStringProperty patient,date,test;
	private SimpleStringProperty status;
	private SimpleStringProperty result,testID;

	public Test_Model(String patient,String date,String test, String status,String result,String testID) {
		this.patient=new SimpleStringProperty(patient);
		this.date=new SimpleStringProperty(date);
		this.test = new SimpleStringProperty(test);
		this.status= new SimpleStringProperty(status);
		this.result = new SimpleStringProperty(result);
		this.testID= new SimpleStringProperty(testID);
	}
	public String getPatient() {
		return patient.get();
	}
	public void setPatient(String patient) {
		this.patient=new SimpleStringProperty(patient);
	}
	public String getDate() {
		return date.get();
	}
	public void setDate(String date) {
		this.date=new SimpleStringProperty(date);
	}
	public String getTest() {
		return test.get();
	}
	public void setTest(String test) {
		this.test=new SimpleStringProperty(test);
	}
	public String getStatus() {
		return status.get();
	}
	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}
	public String getResult() {
		return result.get();
	}
	public void setResult(String result) {
		this.result=new SimpleStringProperty(result);
	}
	public String getTestID() {
		return testID.get();
	}
	public void setTestID(String testID) {
		this.testID=new SimpleStringProperty(testID);
	}

}
