package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleStringProperty;

public class Prescription_Model {

	private SimpleStringProperty medication;
	private SimpleStringProperty dose;
	private SimpleStringProperty count,instructions,dateGiven,status,id;

	public Prescription_Model(String medication, String dose,String count,String instructions,String dateGiven,String status,String ID) {
		this.medication = new SimpleStringProperty(medication);
		this.dose= new SimpleStringProperty(dose);
		this.count= new SimpleStringProperty(count);
		this.instructions= new SimpleStringProperty(instructions);
		this.dateGiven= new SimpleStringProperty(dateGiven);
		this.status= new SimpleStringProperty(status);
		this.id= new SimpleStringProperty(ID);
	}

	public String getMedication() {
		return medication.get();
	}

	public void setMedication(String medication) {
		this.medication =new SimpleStringProperty(medication);
	}

	public String getDose() {
		return dose.get();
	}

	public void setDose(String dose) {
		this.dose = new SimpleStringProperty(dose);
	}

	public String getCount() {
		return count.get();
	}

	public void setCount(String count) {
		this.count = new SimpleStringProperty(count);
	}

	public String getInstructions() {
		return instructions.get();
	}

	public void setInstructions(String instructions) {
		this.instructions = new SimpleStringProperty(instructions);
	}

	public String getDateGiven() {
		return dateGiven.get();
	}

	public void setDateGiven(String dateGiven) {
		this.dateGiven = new SimpleStringProperty(dateGiven);
	}
	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);
	}
	public String getID() {
		return id.get();
	}
	public void setID(String id) {
		this.id= new SimpleStringProperty(id);
	}
	
}
