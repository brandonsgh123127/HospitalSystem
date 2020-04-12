package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Staff_Model {
	
	private SimpleIntegerProperty userID;
	private SimpleIntegerProperty userRole;

	private SimpleStringProperty fName;
	private SimpleStringProperty lName;
	
	
	public Staff_Model(Integer id, Integer role, String fName, String lName) {
		this.userID=new SimpleIntegerProperty(id);
		this.userRole=new SimpleIntegerProperty(role);
		this.fName=new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
	}

	
	public int getUserID() {
		return userID.get();
	}
	public void setUserID(int id) {
		this.userID=new SimpleIntegerProperty(id);
	}
	public int getUserRole() {
		return userRole.get();
	}
	public void setUserRole(Integer role) {
		this.userRole=new SimpleIntegerProperty(role);
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
