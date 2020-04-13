package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PatientVisit_Controller implements Initializable {

	private Stage stage,visitStage;
	private Connection con;
	private Integer userID;
	
	@FXML
	TableView<GenVisit_Model> genTable;
	@FXML
	TableColumn<GenVisit_Model,String> name,gender,DOB, diagnosis,doctor;	
	@FXML
	TableView<Test_Model> tests;
	@FXML
	TableColumn<Test_Model,String> test,status,result,view;
	
	
	@FXML
	TableView<Prescription_Model> prescriptions;
	@FXML
	TableColumn<Prescription_Model,String> medication,dose,count,instructions,dateGiven,statusTest;
	
	
	private ObservableList<GenVisit_Model> genTableContents;
	private ObservableList<Test_Model> testTableContents;	
	private ObservableList<Prescription_Model> prescriptionTableContents;	


	public PatientVisit_Controller(Stage stage, Connection con, Integer userID) {
		this.stage = stage;
		this.con=con;
		this.userID=userID;
		this.visitStage=display();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory("lname"));
		DOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		diagnosis.setCellValueFactory(new PropertyValueFactory("diagnosis"));
		doctor.setCellValueFactory(new PropertyValueFactory("doctor"));

		test.setCellValueFactory(new PropertyValueFactory("test"));
		status.setCellValueFactory(new PropertyValueFactory("status"));
		result.setCellValueFactory(new PropertyValueFactory("result"));
		
		medication.setCellValueFactory(new PropertyValueFactory("medication"));
		dose.setCellValueFactory(new PropertyValueFactory("dose"));
		count.setCellValueFactory(new PropertyValueFactory("count"));
		instructions.setCellValueFactory(new PropertyValueFactory("instructions"));
		dateGiven.setCellValueFactory(new PropertyValueFactory("dateGiven"));
		statusTest.setCellValueFactory(new PropertyValueFactory("status"));

		//Initialize Static Information--> Patient Visit Table
		
		Statement stmt;
		try {
			stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM Patients where patientID="+userID); 
		genTableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			genTableContents.add(new GenVisit_Model(rs.getString(2),rs.getString(10),"",0));
		}
		stmt = con.createStatement();
		 rs=stmt.executeQuery("SELECT * FROM visits where patientID="+userID);
		 int index = 0;
		 while(rs.next()) {
			 genTableContents.get(index).setDiagnosis(rs.getString(7));
			 genTableContents.get(index).setDoctor(rs.getInt(5));
		 }
		 genTable.setItems(genTableContents);

		}
		catch(Exception e) {
			System.err.println("Failed to retrieve patient visit general info!");
			e.printStackTrace();
		}
		
		
	}
	public Stage display() {
		visitStage = new Stage();
		visitStage.initModality(Modality.NONE);
		visitStage.initOwner(stage);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Patient_Visit.fxml"));
		Parent root;
			try {
				loader.setController(this);
				root = loader.load();
    		Scene scene = new Scene(root);
    		visitStage.setScene(scene);
    		//updateTable();
    		visitStage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return visitStage;
	}
	
	
	private void updateTables() {
//		Statement stmt=con.createStatement();
//		ResultSet rs=stmt.executeQuery("SELECT * FROM Patients"); 
//		genTableContents=FXCollections.observableArrayList();
//		while(rs.next()) {
//			boolean res;
//			if(rs.getInt(12) ==1)
//				res=true;
//			else
//				res=false;
//			try {
//			tableContents.add(new Visitor_Model(rs.getInt(1),rs.getString(3),rs.getString(2),rs.getString(10),
//						rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(9),rs.getString(8),rs.getString(11),res,
//						rs.getString(13),rs.getString(14)));
//			}
//			catch(SQLException e) {
//				System.err.println("An error occurred while adding values to table!");
//			}
//		}
//		table.setItems(tableContents);
//		table.refresh();

	}
	public static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
}
