package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Staff_Model {
	
	private SimpleIntegerProperty id;
	private SimpleIntegerProperty role;

	private SimpleStringProperty fName;
	private SimpleStringProperty lName;
	
	
	public Staff_Model(Integer id, Integer role, String fName, String lName) {
		this.id=new SimpleIntegerProperty(id);
		this.role=new SimpleIntegerProperty(role);
		this.fName=new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
	}

	
	public int getID() {
		return id.get();
	}
	public void setID(int id) {
		this.id=new SimpleIntegerProperty(id);
	}
	public int getRole() {
		return role.get();
	}
	public void setRole(Integer role) {
		this.role=new SimpleIntegerProperty(role);
	}
	public String getFName() {
		return fName.get();
	}
	public void setFName(String fName) {
		this.fName=new SimpleStringProperty(fName);
	}
	
	public String getLName() {
		return lName.get();
	}
	public void setLName(String lName) {
		this.lName=new SimpleStringProperty(lName);
	}

}
