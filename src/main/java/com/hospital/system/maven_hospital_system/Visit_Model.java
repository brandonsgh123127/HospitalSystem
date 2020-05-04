package com.hospital.system.maven_hospital_system;

import java.sql.Connection;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Visit_Model {
	private SimpleStringProperty date;
	private SimpleStringProperty reason;
	private SimpleStringProperty doctor;
	private SimpleStringProperty diagnosis,notes;
	private SimpleIntegerProperty visitID,patientID;
	private Connection con;
	private SimpleIntegerProperty followUpID;
	
	/*
	 * Visits Table For Patient Information View
	 */
	public Visit_Model(String date, String reason, String doctor,String diagnosis, Integer visitID,Integer patientID,Integer followUpID,Connection con,String notes) {
		this.date = new SimpleStringProperty(date);
		this.reason = new SimpleStringProperty(reason);
		this.doctor= new SimpleStringProperty(doctor);
		this.diagnosis= new SimpleStringProperty(diagnosis);
		this.visitID= new SimpleIntegerProperty(visitID);
		this.patientID= new SimpleIntegerProperty(patientID);
		this.con=con;
		this.followUpID=new SimpleIntegerProperty(followUpID);
		this.notes= new SimpleStringProperty(notes);
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
	public String getDoctor() {
		return doctor.get();
	}
	public void setDoctor(String doctor) {
		this.doctor= new SimpleStringProperty(doctor);
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
	public Integer getFollowUpID() {
		return followUpID.get();
	}
	public void setFollowUpID(Integer followUpID) {
		this.followUpID= new SimpleIntegerProperty(followUpID);
	}

	public Integer getPatientID() {
		return patientID.get();
	}
	public void setPatientID(Integer patientID) {
		this.patientID= new SimpleIntegerProperty(patientID);
	}
	public Connection getConnection() {
		return con;
	}
	public void setConnection(Connection con) {
		this.con= con;
	}
	public String getNotes() {
		return notes.get();
	}
	public void setNotes(String notes) {
		this.notes= new SimpleStringProperty(notes);
	}
}
