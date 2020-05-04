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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;



/**
 * Doctor-Side modification of Visits
 * 
 * 
 * 
 * __________________________________________\
 * *
 * *
 * *		NOT COMPLETE
 * *
 * *
 * ____________________________________________________
 * @author spada
 *
 */
public class PatientVisitEdit_Controller implements Initializable {

	private Stage stage,visitStage;
	private Statement stmt;
	private Connection con;
	private boolean isNew;
	private Integer userID;
	private Integer visitID,followUpID;
	private Integer presc,testNum;
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
	private Button visitHistory,save,addPrescription,addTest;
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
	public PatientVisitEdit_Controller(Stage stage, Connection con, Integer userID,Integer visitID) {
		this.stage = stage;
		this.con=con;
		isNew=false;
		this.userID=userID;
		this.visitID = visitID;
		System.out.println(visitID +" Visit");
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
		tests.setEditable(true);
	    test.setCellFactory(TextFieldTableCell.forTableColumn());
	    status.setCellFactory(TextFieldTableCell.forTableColumn());
	    result.setCellFactory(TextFieldTableCell.forTableColumn());    
	   
		
		prescriptions.setEditable(true);
	    medication.setCellFactory(TextFieldTableCell.forTableColumn());
	    dose.setCellFactory(TextFieldTableCell.forTableColumn());
	    count.setCellFactory(TextFieldTableCell.forTableColumn());    
	    instructions.setCellFactory(TextFieldTableCell.forTableColumn());    
	    dateGiven.setCellFactory(TextFieldTableCell.forTableColumn());    
	    statusTest.setCellFactory(TextFieldTableCell.forTableColumn());  


		//Allows values to be edited by nurse
		genTable.setEditable(true);
	    doctor.setCellFactory(TextFieldTableCell.<GenVisit_Model,Integer>forTableColumn(new IntegerStringConverter()));
	    
	    /*
	     * If add test is pressed
	     */
	    
	     addTest.setOnAction( new EventHandler<ActionEvent>(){ 
		        @Override
		    	public void handle(ActionEvent e) {
		    		testNum=genTest();
					PreparedStatement stmt2;
					try {
						stmt2 = con.prepareStatement("INSERT INTO Tests VALUES("+testNum+",'"+visitID +"', "+ "-1"+" , "+"-1"+
								","+"'-'"+",'Incomplete',NULL)");
						stmt2.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	    updateTables();
		        		//table.setItems(tableContents);
		        }
		    });
	     
	     /**
	      * Any Modification to Tests Table goes here...
	      */
	     test.setOnEditCommit(new EventHandler<CellEditEvent<Test_Model,String>>(){

				@Override
				public void handle(CellEditEvent<Test_Model, String> event) {
					 ((Test_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).setTest(event.getNewValue());
					 
					 System.out.println(((Test_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).getTest() + " Visit " + visitID);
					try {
						System.out.println("EDIT");
						PreparedStatement stmt=con.prepareStatement("UPDATE tests SET TestTypeID= " +event.getTableView().getItems().get(
						        event.getTablePosition().getRow()).getTest()+" WHERE VisitID = " + visitID + " AND testID= "+ event.getTableView().getItems().get(
								        event.getTablePosition().getRow()).getTestID());
						stmt.execute();
						updateTables();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			});
	     
	     /*
	      * Done
	      */
	    	
	    /*
	     * If Add Prescription is pressed
	     */
	    //Add New Prescription View!!!
	     addPrescription.setOnAction( new EventHandler<ActionEvent>(){ 
		        @Override
		    	public void handle(ActionEvent e) {
		    		presc=genPrescription();
		    		prescriptionTableContents.add(new Prescription_Model("-1", "-","-","-",LocalDate.now().toString(),"-",String.valueOf(presc)));
					PreparedStatement stmt2;
					try {
						stmt2 = con.prepareStatement("INSERT INTO Prescriptions VALUES("+presc+",'"+visitID +"', "+ "-1"+" , '"+"-"+
								"',"+"'-'"+")");
						stmt2.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        	    updateTables();
		        		//table.setItems(tableContents);
		        }
		    });

	    	
	     /**
	      * ANY MODIFICATION TO PRESCRIPTIONS TABLE GOES HERE...
	      */
			medication.setOnEditCommit(new EventHandler<CellEditEvent<Prescription_Model,String>>(){

				@Override
				public void handle(CellEditEvent<Prescription_Model, String> event) {
					 ((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).setMedication(event.getNewValue());
					 
					 System.out.println(((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).getMedication() + " Visit " + visitID);
					try {
						System.out.println("EDIT");
						PreparedStatement stmt=con.prepareStatement("UPDATE prescriptions SET PrescriptionTypeID= " +event.getTableView().getItems().get(
						        event.getTablePosition().getRow()).getMedication()+" WHERE VisitID = " + visitID + " AND prescriptionID= "+ event.getTableView().getItems().get(
								        event.getTablePosition().getRow()).getID());
						stmt.execute();
						updateTables();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			});

			dose.setOnEditCommit(new EventHandler<CellEditEvent<Prescription_Model,String>>(){

				@Override
				public void handle(CellEditEvent<Prescription_Model, String> event) {
					 ((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).setDose(event.getNewValue());
					 
					 System.out.println(((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).getDose() + " Visit " + visitID);
					try {
						System.out.println("EDIT");
						PreparedStatement stmt=con.prepareStatement("UPDATE prescriptions SET Dosage= '" +event.getTableView().getItems().get(
						        event.getTablePosition().getRow()).getDose()+"' WHERE VisitID = " + visitID + " AND prescriptionID= "+ event.getTableView().getItems().get(
								        event.getTablePosition().getRow()).getID());
						stmt.execute();
						updateTables();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			});

			instructions.setOnEditCommit(new EventHandler<CellEditEvent<Prescription_Model,String>>(){

				@Override
				public void handle(CellEditEvent<Prescription_Model, String> event) {
					 ((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).setInstructions(event.getNewValue());
					 
					 System.out.println(((Prescription_Model) event.getTableView().getItems().get(
					            event.getTablePosition().getRow())
					            ).getInstructions() + " Visit " + visitID);
					try {
						System.out.println("EDIT");
						PreparedStatement stmt=con.prepareStatement("UPDATE prescriptions SET Instructions= '" +event.getTableView().getItems().get(
						        event.getTablePosition().getRow()).getInstructions()+"' WHERE VisitID = " + visitID + " AND prescriptionID= "+ event.getTableView().getItems().get(
								        event.getTablePosition().getRow()).getID());
						stmt.execute();
						updateTables();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
			});
	     
			
	     
			
	     
	     /*
	      * END PRESCRIPTION ON EDIT
	      * 
	      */
	    visitStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.out.println("Update table sec");	
				visitStage.hide();
				visitStage.close();
			}});
	    
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
		try {
			System.out.println("PID " +userID + "visitID " + visitID );
			stmt = con.createStatement();

		ResultSet rs=stmt.executeQuery("        SELECT p1.VisitID, p1.patientID, p1.PhysicianID, p1.Results, p2.lName,p2.fName,p2.DateOfBirth" + 
				"       FROM visits AS p1 INNER JOIN patients AS p2 " + 
				"         ON p1.PatientID=" + userID + " AND p2.patientID=" + userID +" AND p1.VisitID=" +visitID); 
		genTableContents=FXCollections.observableArrayList();
		while(rs.next()) {
			System.out.println(rs.getInt(1) +" " + rs.getString(5));
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
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Patient_Visit_Doc_View.fxml"));
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
		testTableContents=FXCollections.observableArrayList();
		prescriptionTableContents=FXCollections.observableArrayList();

		try {
			System.out.println("Update test table");
		Statement stmt=con.createStatement();

		ResultSet rs=stmt.executeQuery("SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg, p2.Date, p4.lName,p4.fName" + 
				"       FROM tests AS p1 INNER JOIN visits AS p2 INNER JOIN testtype as p3 INNER JOIN patients as p4" + 
				"         ON p1.VisitID = " + visitID+ " and p1.VisitID = p2.VisitID and p4.PatientID = p2.PatientID"); 
		while(rs.next()) {
			testTableContents.add(new Test_Model(rs.getString(7) + "," + rs.getString(8),rs.getString(6),String.valueOf(rs.getInt(3)),"-",rs.getString(4),String.valueOf(rs.getInt(2))));
					}
		tests.setItems(testTableContents);
		tests.refresh();
		}
		catch(SQLException e) {
			System.out.println("An Exception occured when adding data to the Test Table!");
		}

		//Fill Prescription Table...
		try {
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT p1.PrescriptionID, p1.VisitID, p1.PrescriptionTypeID, p1.Dosage, p1.Instructions,p2.Date" + 
					"       FROM prescriptions AS p1 INNER JOIN visits AS p2" + 
					"         ON p1.VisitID=p2.VisitID AND p1.VisitID="+visitID); 
			while(rs.next()) {
				System.out.println("Looping prescription table "+ rs.getInt(1));
				prescriptionTableContents.add(new Prescription_Model(rs.getString(3),rs.getString(4),"-1",rs.getString(5),rs.getString(6),"-",String.valueOf(rs.getInt(1))));  //ADD A STATUS STRING IN 
						}
			prescriptions.setItems(prescriptionTableContents);
			prescriptions.refresh();
			}
			catch(SQLException e) {
				System.out.println("An Exception occured when adding data to the Prescription Table!");
			}


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
	
	public int genPrescription() {
		Random ran = new Random();
		int newID = ran.nextInt((int) Math.pow(2,15));
		try {
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM Prescriptions WHERE prescriptionID=" + newID); 
		if(rs.next())
			return genPrescription();
		else
			return (newID);
		}
		catch(SQLException e) {
			System.err.println("Exception in handling new follow up id generation!");
		}
		return -1;
		}
	public int genTest() {
		Random ran = new Random();
		int newID = ran.nextInt((int) Math.pow(2,15));
		try {
		Statement stmt=con.createStatement();
		ResultSet rs=stmt.executeQuery("SELECT * FROM Tests WHERE testID=" + newID); 
		if(rs.next())
			return genPrescription();
		else
			return (newID);
		}
		catch(SQLException e) {
			System.err.println("Exception in handling new follow up id generation!");
		}
		return -1;
		}


}
