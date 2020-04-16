package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Visit_Model {
	private SimpleStringProperty date;
	private SimpleStringProperty reason;
	private SimpleIntegerProperty doctor;
	private SimpleStringProperty diagnosis;
	private SimpleIntegerProperty visitID;
	
	/*
	 * Visits Table For Patient Information View
	 */
	public Visit_Model(String date, String reason, Integer doctor,String diagnosis, Integer visitID) {
		this.date = new SimpleStringProperty(date);
		this.reason = new SimpleStringProperty(reason);
		this.doctor= new SimpleIntegerProperty(doctor);
		this.diagnosis= new SimpleStringProperty(diagnosis);
		this.visitID= new SimpleIntegerProperty(visitID);
	}
	
	public String getDate() {
		return date.get();
	}
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	}
	public String getReason() {
		return reason.get();
	}
	public void setReason(String reason) {
		this.reason = new SimpleStringProperty(reason);
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
}
