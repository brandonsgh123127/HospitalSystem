package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Lab Technician Controller
 * @author spada
 *
 */
public class LabTechController implements Initializable {
	@FXML
	private TableColumn<Test_Model,String> patient,test,result,view,status;
	@FXML
	private TableColumn<Test_Model,String> unPatient,unTest,unDate;
	@FXML
	private TableView<Test_Model> table;
	@FXML
	private TableView<Test_Model> untable;
	@FXML
	private TextField search;
	private Connection con;
	private Stage stage,testStage;
	private Scene scene;
	private Integer techID;
	
	private ObservableList<Test_Model> tableContents;	

	public LabTechController(Stage stage, Scene scene, Connection con, Integer techId) {
		this.techID=techId;
		this.stage=stage;
		this.scene=scene;
		this.con=con;

		try {
			display();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void display() throws IOException {
		//Load the FXML file
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Lab_Home.fxml"));
		loader.setController(this);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		try {
			updateTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        stage.show();
	}
	
	public Stage getDisplay() {
		return stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Assigned tests
		patient.setCellValueFactory(new PropertyValueFactory("patient"));
		test.setCellValueFactory(new PropertyValueFactory("test"));
		result.setCellValueFactory(new PropertyValueFactory("result"));
		status.setCellValueFactory(new PropertyValueFactory("status"));
		//UnAssigned tests
		unPatient.setCellValueFactory(new PropertyValueFactory("patient"));
		unTest.setCellValueFactory(new PropertyValueFactory("test"));
		unDate.setCellValueFactory(new PropertyValueFactory("date"));
		
		//Table On Double Click, view test...
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	if(((Test_Model) table.getSelectionModel().getSelectedItems().get(0))!=null) {
		        	System.out.println(((Test_Model) table.getSelectionModel().getSelectedItems().get(0)).getTestID() +"Test");
		        	TestEdit_Controller visit= new TestEdit_Controller(stage,con,((Test_Model) table.getSelectionModel().getSelectedItems().get(0)).getTestID());
	            	testStage = visit.getDisplay();
	            	testStage.setOnHidden( new EventHandler<WindowEvent>() {
	        			@Override
	        			public void handle(WindowEvent event) {
	        				try {
	        					System.out.println("Update table Lab");
	        					updateTables();
	        				} catch (SQLException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}	
	        			}});           		
		        	}

		        }
		    }
		});
		
		untable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	if(((Test_Model) untable.getSelectionModel().getSelectedItems().get(0))!=null)
		        	// Add Unassigned patients to Patients
		        	try {
						PreparedStatement stmt2=con.prepareStatement("UPDATE Tests SET TechID="+techID+" WHERE TestID=" + ((Test_Model) untable.getSelectionModel().getSelectedItems().get(0)).getTestID());
						stmt2.execute();
						updateTables();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
		});
		
	}
	
	
	private void updateTables() throws SQLException{
		//Display All Upcoming Tests >>>>>>>
		Statement stmt;
		tableContents=FXCollections.observableArrayList();
		try {
			stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("        SELECT p1.TestID, p1.TestTypeID,p1.VisitID,p1.Result, p2.lName,p2.fName,p2.DateOfBirth,p3.Date,p1.Status" + 
				"       FROM Tests AS p1 INNER JOIN patients AS p2 INNER JOIN visits AS p3" + 
				"         ON p1.TechID=" + techID +" AND p2.patientID=p3.patientID AND p1.visitID= p3.visitID"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
				tableContents.add(new Test_Model(rs.getString(5) + "," + rs.getString(6),rs.getString(8),String.valueOf(rs.getInt(2)),rs.getString(9),rs.getString(4),String.valueOf(rs.getInt(1))));
		}
		 table.setItems(tableContents);
		}
		catch(Exception e) {
			System.err.println("Failed to retrieve patient visit general info!");
			e.printStackTrace();
		}
//Display All Upcoming Tests where the Test is not assigned to a Tech
		tableContents=FXCollections.observableArrayList();
		try {
			stmt = con.createStatement();
		ResultSet rs=stmt.executeQuery("        SELECT p1.TestID, p1.TestTypeID,p1.VisitID,p1.Result, p2.lName,p2.fName,p2.DateOfBirth,p3.Date" + 
				"       FROM Tests AS p1 INNER JOIN patients AS p2 INNER JOIN visits AS p3" + 
				"         ON p1.TechID=-1"); 
		tableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			tableContents.add(new Test_Model(rs.getString(5) + "," + rs.getString(6),rs.getString(8),String.valueOf(rs.getInt(2)),"Incomplete",rs.getString(4),String.valueOf(rs.getInt(1))));
		}
		 untable.setItems(tableContents);
		}
		catch(Exception e) {
			System.err.println("Failed to retrieve patient visit general info!");
			e.printStackTrace();
		}
}

	}



