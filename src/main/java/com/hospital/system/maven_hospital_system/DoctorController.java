package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DoctorController implements Initializable {
	@FXML
	private TableColumn<GenVisit_Model,String> patientName,DOB,patientGender,roomNum;
	@FXML
	private TableColumn<GenVisit_Model,String> unPatientName,unDOB,unGender,unRoomNum;

	@FXML
	private TableColumn<GenVisit_Model,Button> add;
	
	@FXML
	private TableView<GenVisit_Model> table;
	@FXML
	private TableView<GenVisit_Model> unTable;
	@FXML
	private TextField searchBar;

	private Stage docStage,stage,visitStage;
	private Scene scene;
	private Connection con;
	private Integer docID,nurseID;
	
	private ObservableList<GenVisit_Model> tableContents;	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	public DoctorController(Stage stage, Scene scene, Connection con,Integer id) {
		
	}
	
	
}