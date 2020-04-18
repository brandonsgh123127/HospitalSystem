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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class that will allow nurse to view Doctor's Patients
 * @author spada
 *
 */
public class DoctorView implements Initializable {
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
		patientName.setCellValueFactory(new PropertyValueFactory("name"));
		DOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		patientGender.setCellValueFactory(new PropertyValueFactory("gender"));
		roomNum.setCellValueFactory(new PropertyValueFactory("room"));
		
		unPatientName.setCellValueFactory(new PropertyValueFactory("name"));
		unDOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		unGender.setCellValueFactory(new PropertyValueFactory("gender"));
		unRoomNum.setCellValueFactory(new PropertyValueFactory("room"));
		
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	if(((GenVisit_Model) table.getSelectionModel().getSelectedItems().get(0))!=null) {
		        	System.out.println(((GenVisit_Model) table.getSelectionModel().getSelectedItems().get(0)).getPatientID() +"PID");
		        	PatientVisit_Controller visit= new PatientVisit_Controller(stage,con,((GenVisit_Model) table.getSelectionModel().getSelectedItems().get(0)).getPatientID(),((GenVisit_Model) table.getSelectionModel().getSelectedItems().get(0)).getVisitID());
	            	visitStage = visit.getDisplay();
	            	visitStage.setOnHidden( new EventHandler<WindowEvent>() {
	        			@Override
	        			public void handle(WindowEvent event) {
	        				try {
	        					System.out.println("Update table sec");
	        					updateTable();
	        				} catch (SQLException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}	
	        			}});           		
		        	}

		        }
		    }
		});
		
		unTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	if(((GenVisit_Model) unTable.getSelectionModel().getSelectedItems().get(0))!=null)
		        	// Add Unassigned patients to Patients
		        	try {
						PreparedStatement stmt2=con.prepareStatement("UPDATE Visits SET PhysicianID="+docID+" WHERE PatientID=" + ((GenVisit_Model) unTable.getSelectionModel().getSelectedItems().get(0)).getPatientID()+"");
						stmt2.execute();
						updateTable();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
		});

	}
	public DoctorView(Stage stage, Scene scene, Connection con, Integer docID, Integer nurseID) {
		this.stage=stage;
		this.scene=scene;
		this.con=con;
		this.docID=docID;
		this.nurseID=nurseID;
		this.docStage = new Stage();
		display();
	}
	public Stage getDisplay() {
		return docStage;
	}
	private void display() {
	     docStage.initModality(Modality.APPLICATION_MODAL);
	     //docStage.initOwner(Stage);
	     FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Nurse_View_Doc.fxml"));
	 		Parent root;
				try {
					loader.setController(this);
					root = loader.load();
	     		Scene scene = new Scene(root);
	     		docStage.setScene(scene);
	     		docStage.show();
	     		updateTable();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	private void updateTable() throws SQLException{
		//Display All Upcoming Patients' Appointments >>>>>>>
				Statement stmt;
				tableContents=FXCollections.observableArrayList();
				try {
					stmt = con.createStatement();
				ResultSet rs=stmt.executeQuery("        SELECT p1.VisitID, p1.followUpID,p1.Date,p1.patientID, p1.PhysicianID,p2.lName,p2.fName,p2.DateOfBirth" + 
						"       FROM visits AS p1 INNER JOIN patients AS p2 " + 
						"         ON p1.PhysicianID=" + docID +" AND p1.patientID=p2.patientID"); 
				tableContents=FXCollections.observableArrayList();
				while(rs.next()) {
					if(LOCAL_DATE(rs.getString(3)).compareTo(LocalDate.now())>-1)
						tableContents.add(new GenVisit_Model(rs.getString(6) + ","+rs.getString(7),rs.getString(3),"-",rs.getInt(5),rs.getInt(1),rs.getInt(4)));
				}
				 table.setItems(tableContents);
				}
				catch(Exception e) {
					System.err.println("Failed to retrieve patient visit general info!");
					e.printStackTrace();
				}
	  //Display All Upcoming Patients' Appointments where the patient is not assigned to a doctor
				tableContents=FXCollections.observableArrayList();
				try {
					stmt = con.createStatement();
				ResultSet rs=stmt.executeQuery("        SELECT p1.VisitID, p1.followUpID,p1.Date,p1.patientID, p1.PhysicianID,p2.lName,p2.fName,p2.DateOfBirth" + 
						"       FROM visits AS p1 INNER JOIN patients AS p2 " + 
						"         ON p1.PhysicianID=-1 AND p1.patientID=p2.patientID"); 
				tableContents=FXCollections.observableArrayList();
				while(rs.next()) {
					tableContents.add(new GenVisit_Model(rs.getString(6) + ","+rs.getString(7),rs.getString(3),"-",rs.getInt(5),rs.getInt(1),rs.getInt(4)));
				}
				 unTable.setItems(tableContents);
				}
				catch(Exception e) {
					System.err.println("Failed to retrieve patient visit general info!");
					e.printStackTrace();
				}
	}
	//Convert DB String to LocalDate object
	private static final LocalDate LOCAL_DATE (String dateString){
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    LocalDate localDate = LocalDate.parse(dateString, formatter);
	    return localDate;
	}
	

}
