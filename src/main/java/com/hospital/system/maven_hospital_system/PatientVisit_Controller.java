package com.hospital.system.maven_hospital_system;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;


/*
 * Nurse's Version of Modifying the Patient's Visit-- Can Modify:
 * 	Doctor Number
 * 	Notes
 * Symptoms
 */
public class PatientVisit_Controller implements Initializable {

	private Stage stage,visitStage;
	private Connection con;
	private boolean isNew;
	private Integer userID;
	private Integer visitID,followUpID;
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
	private Button visitHistory,save;
	@FXML
	private TextArea symptoms,notes;
	@FXML
	TableView<Prescription_Model> prescriptions;
	@FXML
	TableColumn<Prescription_Model,String> medication,dose,count,instructions,dateGiven,statusTest;
	
	
	private ObservableList<GenVisit_Model> genTableContents;
	private ObservableList<Test_Model> testTableContents;	
	private ObservableList<Prescription_Model> prescriptionTableContents;	

//Existing Visit
	public PatientVisit_Controller(Stage stage, Connection con, Integer userID,Integer visitID) {
		this.stage = stage;
		this.con=con;
		isNew=false;
		this.userID=userID;
		this.visitID = visitID;
		System.out.println(userID);
		this.visitStage=display();
	}
	
	public PatientVisit_Controller(Integer userID, Integer visitID,Connection con) {
		this.stage = new Stage();
		this.userID=userID;
		isNew = true;
		this.visitID = visitID;
		this.con=con;
		System.out.println(userID);
		this.visitStage=display();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		name.setCellValueFactory(new PropertyValueFactory("name"));
		DOB.setCellValueFactory(new PropertyValueFactory("DOB"));
		diagnosis.setCellValueFactory(new PropertyValueFactory("diagnosis"));
		doctor.setCellValueFactory(new PropertyValueFactory("doctor"));
		if(isNew)
			save.setVisible(true);
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
	    //if new instance, able to modify visit row
	    if(isNew) {
	    	save.setVisible(true);
	    	Statement stmt;
			try {
				stmt = con.createStatement();
		    	ResultSet rs = stmt.executeQuery("SELECT * FROM Visits  WHERE patientID ="+userID+ " ORDER BY Date DESC LIMIT 1");
		    	while(rs.next())
		    	{
		    		visitID=rs.getInt(6);
		    		followUpID=genFollowUp();
			    	Visit_Model temp = new Visit_Model("2045-08-28", "-", "-1","-", visitID,userID,followUpID,con,"");
			    	temp.setVisitID(visitID);
			    	temp.setFollowUpID(followUpID);
			    	System.out.println("visit ID" + temp.getVisitID());
					PreparedStatement stmt2=con.prepareStatement("INSERT INTO Visits VALUES("+temp.getVisitID()+",'"+temp.getDate() +"', '"+ temp.getReason()+"' , "+userID+
							","+temp.getDoctor()+","+ temp.getFollowUpID() + ",'" +temp.getDiagnosis()+"', '" + temp.getNotes() + "' )");
					stmt2.execute();

		    	}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	
	    }
	    visitStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.out.println("Update table sec");	
				visitStage.hide();
				visitStage.close();
			}});
	    
	    //When save button is pressed, save data to new entry
	    save.setOnAction(event -> {
	    	Visit_Model temp = new Visit_Model("05-24-2050", "-", "-1","-", visitID,userID,followUpID,con,"");
	    	Statement stmt;
	    	//followUpID = genFollowUp();
			try {
				stmt = con.createStatement();
//				temp.setVisitID(temp.getFollowUpID());
//				temp.setFollowUpID(genFollowUp());
//				followUpID=temp.getFollowUpID();
				PreparedStatement stmt2=con.prepareStatement("INSERT INTO Visits VALUES("+temp.getVisitID()+",'"+temp.getDate() +"', '"+ temp.getReason()+"' , "+userID+
						","+temp.getDoctor()+","+ temp.getFollowUpID() + ",'" +temp.getDiagnosis()+"', '" +temp.getNotes()+"')");
				stmt2.execute();

			} catch (SQLException e1) {
				try {
					PreparedStatement stmt2=con.prepareStatement("UPDATE Visits SET FollowUpID="+temp.getFollowUpID()+",Date='"+temp.getDate() +"', Reason='"+ temp.getReason()+"' , PatientID="+userID+
							",PhysicianID="+temp.getDoctor()+",followUpID="+ genFollowUp() + ",Results='" +temp.getDiagnosis()+"' WHERE patientID =" + userID);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					
					System.out.println("Exception in relation to adding/modifying visit values.  Please Check Values entered in tables!");
					e2.printStackTrace();
				}
			}	    
			visitStage.hide();
			visitStage.close();
	    });

	    
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
	  		//When Nurse Or Doctor Press Enter on Symptoms, update Value
	  		symptoms.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
	  			@Override
				public void handle(KeyEvent key) {
					if(key.getCode()==KeyCode.ENTER) {
	  					try {
	  						System.out.println("Enter Key");
							PreparedStatement stmt=con.prepareStatement("UPDATE `Visits` SET Reason = '" + symptoms.getText()+"' WHERE VisitID = " + visitID);
							stmt.execute();
							updateTables();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							updateTables();
						}

					}
				}
			});
	  		//When Nurse Or Doctor Press Enter on Symptoms, update Value
	  		notes.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
	  			@Override
				public void handle(KeyEvent key) {
					if(key.getCode()==KeyCode.ENTER) {
	  					try {
	  						System.out.println("Enter Key");
	  						PreparedStatement stmt=con.prepareStatement("UPDATE `Visits` SET notes = '" + notes.getText()+"' WHERE VisitID = " + visitID);
	  						stmt.execute();
	  					} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
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
	public Stage getDisplay() {
		return visitStage;
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
		ResultSet rs=stmt.executeQuery("SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg, p3.TestType,p2.Date,p4.lName,p4.fName,p1.Status" + 
				"       FROM tests AS p1 INNER JOIN visits AS p2 INNER JOIN testtype as p3 INNER JOIN patients as p4" + 
				"         ON p1.VisitID= " +visitID + " AND p2.VisitID=" + visitID +" and p1.testTypeID = p3.TestTypeID AND p4.PatientID = p2.PatientID"); 
		testTableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			testTableContents.add(new Test_Model(rs.getString(8) + "," + rs.getString(9),rs.getString(7),rs.getString(6),rs.getString(10),rs.getString(4),String.valueOf(rs.getInt(2))));
					}
		}
		catch(SQLException e) {
			System.out.println("An Exception occured when adding data to the Test Table!");
			e.printStackTrace();
		}
		tests.setItems(testTableContents);
		tests.refresh();
		//Fill Prescription Table...
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT p1.PrescriptionID, p1.VisitID, p1.PrescriptionTypeID, p1.Dosage, p1.Instructions, p3.PrescriptionType,p2.Date,p2.reason,p2.notes" + 
					"       FROM prescriptions AS p1 INNER JOIN visits AS p2 INNER JOIN prescriptiontypes as p3" + 
					"         ON p1.VisitID= " +visitID + " AND p2.VisitID=" + visitID +" and p1.PrescriptionTypeID = p3.PrescriptionTypeID"); 
			prescriptionTableContents=FXCollections.observableArrayList();
			while(rs.next()) {
				prescriptionTableContents.add(new Prescription_Model(rs.getString(6),rs.getString(4),"1",rs.getString(5),rs.getString(7),"-",String.valueOf(rs.getInt(1))));  //ADD A STATUS STRING IN 
						}
			}
			catch(SQLException e) {
				System.out.println("An Exception occured when adding data to the Prescription Table!");
			}
			prescriptions.setItems(prescriptionTableContents);
			prescriptions.refresh();

			//Update text areas
			try {
				Statement stmt=con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Visits WHERE VisitID = "+ visitID);
				if(rs.next()) {
					notes.setText(rs.getString(8));
					symptoms.setText(rs.getString(3));
				}

			}
			catch(SQLException e) {
				System.err.println("Failed to set note text and symptom text!");
			}
	}
	public int getVisitID() {
		return visitID;
	}
	
	public int genFollowUp() {
		Random ran = new Random();
		int newID = ran.nextInt((int) Math.pow(2,12));
		try {
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM Visits WHERE visitID=" + newID); 
		if(rs.next())
			return genFollowUp();
		else
			return (newID);
		}
		catch(SQLException e) {
			System.err.println("Exception in handling new follow up id generation!");
		}
		return -1;
		}

}
