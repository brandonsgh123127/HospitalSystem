package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

public class PatientVisit_Controller implements Initializable {

	private Stage stage,visitStage;
	private Connection con;
	private Integer userID;
	private Integer visitID;
	@FXML
	private TableView<GenVisit_Model> genTable;
	@FXML
	private TableColumn<GenVisit_Model,String> name,gender,DOB, diagnosis;
	@FXML
	private TableColumn<GenVisit_Model,Integer> doctor;	
	@FXML
	private TableView<Test_Model> tests;
	@FXML
	private TableColumn<Test_Model,String> test,status,result,view;
	@FXML
	private Button visitHistory;
	@FXML
	private TextArea symptoms,notes;
	@FXML
	TableView<Prescription_Model> prescriptions;
	@FXML
	TableColumn<Prescription_Model,String> medication,dose,count,instructions,dateGiven,statusTest;
	
	
	private ObservableList<GenVisit_Model> genTableContents;
	private ObservableList<Test_Model> testTableContents;	
	private ObservableList<Prescription_Model> prescriptionTableContents;	


	public PatientVisit_Controller(Stage stage, Connection con, Integer userID,Integer visitID) {
		this.stage = stage;
		this.con=con;
		this.userID=userID;
		this.visitID = visitID;
		System.out.println(userID);
		this.visitStage=display();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory("name"));
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

		//Allows values to be edited by nurse
		genTable.setEditable(true);
	    doctor.setCellFactory(TextFieldTableCell.<GenVisit_Model,Integer>forTableColumn(new IntegerStringConverter()));

	    
	  //When enter key is pressed
	  		doctor.setOnEditCommit(new EventHandler<CellEditEvent<GenVisit_Model,Integer>>(){
	  			@Override
	  			public void handle(CellEditEvent<GenVisit_Model, Integer> e) {
	  				try {
	  					((GenVisit_Model) e.getTableView().getItems().get(
	  					            e.getTablePosition().getRow())
	  					            ).setDoctor(e.getNewValue());
	  					PreparedStatement stmt=con.prepareStatement("UPDATE `Visits` SET PhysicianID = " + ((GenVisit_Model) e.getTableView().getItems().get(
  					            e.getTablePosition().getRow())
  					            ).getDoctor() + " WHERE PatientID = " + ((GenVisit_Model) e.getTableView().getItems().get(
  		  					            e.getTablePosition().getRow())
  		  					            ).getPatientID() + " AND visitID = " + ((GenVisit_Model) e.getTableView().getItems().get(
  		  	  					            e.getTablePosition().getRow())
  		  	  					            ).getVisitID());
	  					stmt.execute();
	  				} catch (Exception e1) {
	  					// TODO Auto-generated catch block
	  					e1.printStackTrace();
	  				}
	  				System.out.println("Doctor CHANGE");
	  				
	  			}
	  		});
	    
		//Initialize Static Information--> Patient Visit Table
		Statement stmt;
		try {
			stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("        SELECT p1.VisitID, p1.patientID, p1.PhysicianID, p1.Results, p2.lName,p2.fName,p2.DateOfBirth" + 
				"       FROM visits AS p1 INNER JOIN patients AS p2 " + 
				"         ON p1.PatientID=" + userID + " AND p2.patientID=" + userID +" AND p1.VisitID=" +visitID); 
		genTableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			genTableContents.add(new GenVisit_Model(rs.getString(5) + ","+rs.getString(6),rs.getString(7),rs.getString(4),rs.getInt(3),rs.getInt(1),rs.getInt(2)));
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
    		updateTables();
    		visitStage.show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return visitStage;
	}
	
	/**
	 * Updates the Test and Prescription Tables...
	 */
	private void updateTables() {
		
		try {
			System.out.println("Update test table");
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg, p3.TestType " + 
				"       FROM tests AS p1 INNER JOIN visits AS p2 INNER JOIN testtype as p3" + 
				"         ON p1.VisitID= " +visitID + " AND p2.VisitID=" + visitID +" and p1.testTypeID = p3.TestTypeID"); 
		System.out.println("SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg, p3.TestType " + 
				"       FROM tests AS p1 INNER JOIN visits AS p2 INNER JOIN testtype as p3" + 
				"         ON p1.VisitID= " +visitID + " AND p2.VisitID=" + visitID +" and p1.testTypeID = p3.TestTypeID");
		testTableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			testTableContents.add(new Test_Model(rs.getString(6),"-",rs.getString(4)));
					}
		}
		catch(SQLException e) {
			System.out.println("An Exception occured when adding data to the Test Table!");
		}
		tests.setItems(testTableContents);
		tests.refresh();
		//Fill Prescription Table...
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT p1.PrescriptionID, p1.VisitID, p1.PrescriptionTypeID, p1.Dosage, p1.Instructions, p3.PrescriptionType,p2.Date" + 
					"       FROM prescriptions AS p1 INNER JOIN visits AS p2 INNER JOIN prescriptiontypes as p3" + 
					"         ON p1.VisitID= " +visitID + " AND p2.VisitID=" + visitID +" and p1.PrescriptionTypeID = p3.PrescriptionTypeID"); 
			prescriptionTableContents=FXCollections.observableArrayList();
			while(rs.next()) {
				prescriptionTableContents.add(new Prescription_Model(rs.getString(6),rs.getString(4),"1",rs.getString(5),rs.getString(7),"-"));  //ADD A STATUS STRING IN 
						}
			}
			catch(SQLException e) {
				System.out.println("An Exception occured when adding data to the Prescription Table!");
			}
			prescriptions.setItems(prescriptionTableContents);
			prescriptions.refresh();

	}
	public int getVisitID() {
		return visitID;
	}

}
