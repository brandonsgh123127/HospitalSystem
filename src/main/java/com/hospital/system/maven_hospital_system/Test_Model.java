package com.hospital.system.maven_hospital_system;

import javafx.beans.property.SimpleStringProperty;

public class Test_Model {
	
	private SimpleStringProperty test;
	private SimpleStringProperty status;
	private SimpleStringProperty result;

	public Test_Model(String test, String status,String result) {
		this.test = new SimpleStringProperty(test);
		this.status= new SimpleStringProperty(status);
		this.result = new SimpleStringProperty(result);
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

}
