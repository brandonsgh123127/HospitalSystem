package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class DoctorController implements Initializable {
	@FXML
	private TableColumn patientName,DOB,patientGender,roomNum;
	@FXML
	private TableColumn unPatientName,unDOB,unGender,unRoomNum;

	@FXML
	private TextField searchBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	
}
