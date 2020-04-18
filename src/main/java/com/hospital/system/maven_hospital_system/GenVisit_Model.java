package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GenVisit_Model {
	private SimpleStringProperty name;
	private SimpleStringProperty DOB;
	private SimpleIntegerProperty doctor,visitID,patientID;
	private SimpleStringProperty diagnosis;
	private SimpleStringProperty room,gender;
	
	/*
	 * Visits Table For Patient Information View
	 */
	public GenVisit_Model(String name, String DOB,String diagnosis,Integer doctor,Integer visitID,Integer patientID) {
		this.name = new SimpleStringProperty(name);
		this.DOB = new SimpleStringProperty(DOB);
		this.doctor= new SimpleIntegerProperty(doctor);
		this.diagnosis= new SimpleStringProperty(diagnosis);
		this.visitID = new SimpleIntegerProperty(visitID);
		this.patientID= new SimpleIntegerProperty(patientID);

	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name= new SimpleStringProperty(name);
	}
	public String getDOB() {
		return DOB.get();
	}
	public void setDOB(String DOB) {
		this.DOB = new SimpleStringProperty(DOB);
	}
	public Integer getDoctor() {
		return doctor.get();
	}
	public void setDoctor(Integer doctor) {
		this.doctor= new SimpleIntegerProperty(doctor);
	}
	public String getDiagnosis() {
		return diagnosis.get();
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis= new SimpleStringProperty(diagnosis);
	}
	public Integer getVisitID() {
		return visitID.get();
	}
	public void setVisitID(Integer visitID) {
		this.visitID= new SimpleIntegerProperty(visitID);
	}
	public Integer getPatientID() {
		return patientID.get();
	}
	public void setPatientID(Integer patientID) {
		this.patientID= new SimpleIntegerProperty(patientID);
	}
}
