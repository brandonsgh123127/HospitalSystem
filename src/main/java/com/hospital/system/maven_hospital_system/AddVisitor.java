package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

import java.sql.Connection;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class AddVisitor implements Initializable{
	private Callback<TableColumn<Visit_Model, Button>, TableCell<Visit_Model, Button>> cellFactory;

	private  Visitor_Model temp;
	private Integer userID=-1;
	
	private Stage dialog,origStage;
	private Scene scene;
	private Connection con;
	private HashSet<TextField> isValid = new HashSet<>();
	Boolean noErrors;
	@FXML
	private TextField fName,lName,address,city,state,zip,country,phone,email,insuranceCarrier,insuranceID;
	private ArrayList<TextField> fields;
	@FXML
	DatePicker DOB;
	@FXML
	Button submitButton;
	@FXML
	TableView<Visit_Model> table;
	@FXML
	TableColumn<Visit_Model,String> dateColumn,reasonColumn,doctorColumn;
	@FXML
	TableColumn<Visit_Model,Button>moreColumn;
	@FXML
	private ObservableList<Visit_Model> tableContents;

	@FXML
	CheckBox yesCitizen,noCitizen;
	
	public AddVisitor(Stage stage, Connection con) {
		this.con= con;
		fields = new ArrayList<>();
		this.origStage = stage;
		dialog = new Stage();
		display();
		
	}
	public AddVisitor(Stage stage, Connection con, Integer userID) {
		this.userID=userID;
		this.con= con;
		fields = new ArrayList<>();
		this.origStage = stage;
		dialog = new Stage();
		display();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noErrors = false;
		fields.add(fName);fields.add(city);fields.add(phone);fields.add(insuranceID);
		fields.add(lName);fields.add(state);fields.add(email);
		fields.add(address);fields.add(zip);fields.add(insuranceCarrier);
		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		dateColumn.setEditable(true);
		table.setEditable(true);
		dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	    reasonColumn.setEditable(false);doctorColumn.setEditable(false);

		reasonColumn.setCellValueFactory(new PropertyValueFactory("reason"));
		doctorColumn.setCellValueFactory(new PropertyValueFactory("doctor"));
		
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
			
		    @Override 
		    public void handle(MouseEvent event) {  //double click on user to change info...
		    	
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {    //TABLE VIEWS! !
		        	if(((Visit_Model)table.getSelectionModel().getSelectedItems().get(0))!= null)
		        	{
		        		if((table.getSelectionModel().getSelectedCells().get(0).getTableColumn().isEditable())) 
		        		{
		        			System.out.println("Date");
		        		}
		        		else {
		        		PatientVisit_Controller visits = new PatientVisit_Controller(origStage,con,userID,((Visit_Model)table.getSelectionModel().getSelectedItems().get(0)).getVisitID());
		        	visits.getDisplay().setOnHidden(new EventHandler<WindowEvent>() {
	        			@Override
	        			public void handle(WindowEvent event) {
	        				try {
	        					System.out.println("Close Window");
	        					updateTable();
	        				} catch (SQLException e) {
	        					// TODO Auto-generated catch block
	        					e.printStackTrace();
	        				}	
	        			}});  
		        		}
		        	}	
		        	else {
		        		try {
		    			Statement stmt=con.createStatement();
		    			ResultSet rs=stmt.executeQuery("SELECT * FROM Patients WHERE patientID=" + userID); 
		    			if(rs.next()) {
		    				Integer firstVis = genFollowUp();
		        		PatientVisit_Controller visit = new PatientVisit_Controller(origStage,con,userID,firstVis);
		        		System.out.println("INSERT INTO Visits VALUES("+firstVis+","+"'"+LocalDate.now()+"'"+", '-' , "+userID+","+"-1" +", "+ genFollowUp()+ ", '-','-' )");
						PreparedStatement stmt2=con.prepareStatement("INSERT INTO Visits VALUES("+firstVis+","+"'"+LocalDate.now()+"'" +", "+"'-'," + userID+","+"-1" +", "+ genFollowUp()+ ", '-', '-' )");
						stmt2.execute();
		    			}
		    			updateTable();
		        		}
		    			catch(Exception e) {
		    				System.err.println("An error occurred when creating a visit on empty table on new patient!!");
		    				e.printStackTrace();
		    			}
		        		}
		        	}
		        	}
		    }
		);
				submitButton.setOnAction(
		        new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	System.out.println(fName.getText().isEmpty());
		            	if(isValid.isEmpty()) {
		            		System.out.println("Checking null");
			            	for(TextField field:fields) {
			            		if(field.getText().isEmpty()) {
			            			System.out.println(field.getId() + " is Empty!");
			            			isValid.add(field);
			            		}

			            	}
			            	if(isValid.isEmpty()) {
			            		noErrors = true;
			            	}
			            	else
			            		noErrors=false;
		            		}
		            	else {
		            			for(TextField field:fields) {
		            				if(isValid.contains(field)&&!field.getText().isEmpty()) {
		            					isValid.remove(field);
		            				}
		            		}
		            	}
		            	if(noErrors) {
							try {
								addNew();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		            	}
		            }
		         });
				
				
				dateColumn.setOnEditCommit(new EventHandler<CellEditEvent<Visit_Model,String>>(){

					@Override
					public void handle(CellEditEvent<Visit_Model, String> event) {
						event.getTableView().getItems().get(
						        event.getTablePosition().getRow()).setDate(event.getNewValue());
						PreparedStatement stmt;
						try {
							stmt = con.prepareStatement("UPDATE Visits SET Date= '" +event.getTableView().getItems().get(
							        event.getTablePosition().getRow()).getDate()+"' WHERE VisitID = " + event.getTableView().getItems().get(
									        event.getTablePosition().getRow()).getVisitID());
							stmt.execute();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						
					}
					
				});
				
				cellFactory = new Callback<TableColumn<Visit_Model,Button>, TableCell<Visit_Model,Button>>() {
	                public TableCell call(TableColumn p) {
	                    return new AddVisitCell();
	                }
				};
				moreColumn.setCellFactory(cellFactory);
								
	}
	public Stage getDisplay() {return dialog;}
	public void display() {
	     dialog.initModality(Modality.NONE);
         dialog.initOwner(origStage);
 		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Patient_Information.fxml"));
 		Parent root;
			try {
				loader.setController(this);
				root = loader.load();
     		Scene scene = new Scene(root);
     		dialog.setScene(scene);
     		dialog.show();
     		updateTable();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * Add New/Existing Users to DB
	 * @throws SQLException
	 * @throws NoSuchAlgorithmException
	 */
	@FXML
	public void addNew() throws SQLException, NoSuchAlgorithmException{
		if(userID == -1){
			userID=genID();
		}
			if(yesCitizen.isSelected()) {
				temp =new Visitor_Model(userID,fName.getText(),lName.getText(),DOB.getValue().toString(),
 													address.getText(),city.getText(),state.getText(),zip.getText(),email.getText(),phone.getText(),"United States",true,
 													insuranceID.getText(),insuranceCarrier.getText());
			}
			else {
	 			temp =new Visitor_Model(userID,fName.getText(),lName.getText(),DOB.getValue().toString(),
							address.getText(),city.getText(),state.getText(),zip.getText(),email.getText(),phone.getText(),"Other",false,
							insuranceID.getText(),insuranceCarrier.getText());
				
			}
			try {
				PreparedStatement stmt;
				if(temp.isResident()) { //If resident, execute
					stmt=con.prepareStatement("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
									",'"+temp.getLName()+"', '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
									+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
									"United States"  + "' , " + "1" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
					System.out.println("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
							","+temp.getLName()+", '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
							+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
							"United States" + "' , " + "1" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
				}
				else {//else
					stmt=con.prepareStatement("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
							", '"+temp.getLName()+"' , '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
							+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
							"Other" + "' , " + "0" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
			System.out.println("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
					","+temp.getLName()+", '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
					+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
					"Other" + "' , " + "0" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
					
				}
					stmt.execute();
			} //IF ENTRY already exists, update the information through query
			catch(SQLException e) {
				System.out.println("Updating Entry..." +temp.getUserID());
				PreparedStatement stmt=con.prepareStatement("UPDATE `Patients` SET lName = '" + temp.getLName() + "',fName = '" + temp.getFName() + "', Address= '"
															+temp.getAddress()+"' , City= '" + temp.getCity() + "' , State= '" + temp.getState() + "' , Zip = '" +
															temp.getZip() + "' , Phone = '" + temp.getPhone() + "' , Email = '" + temp.getEmail() + "' , Country = " +
															"'United States" + "' , insuranceID = '" + temp.getInsuranceID() + "' , insuranceProvider = '" + temp.getInsuranceProvider()
															+ "' where patientID = "+temp.getUserID());
				stmt.execute();
				}
			dialog.hide();
			dialog.close();
		}
		

	/*
	 * Generate New ID for user
	 */
		private Integer genID() throws SQLException {

			Random ran = new Random();
			int newID = ran.nextInt((int) Math.pow(2,15));
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM Patients WHERE patientID=" + newID); 
			if(rs.next())
				return genID();
			else
				return (newID);
		}
		
		private void updateTable() throws SQLException {
			Statement stmt=con.createStatement();
			//First Update Text Fields
			try {
				ResultSet rs = stmt.executeQuery("Select * FROM Patients WHERE patientID = "+userID);
				while(rs.next()) {
					System.out.println("Existing");
					fName.setText(rs.getString(3));
					lName.setText(rs.getString(2));
						address.setText(rs.getString(4));
						city.setText(rs.getString(5));
						state.setText(rs.getString(6));
						zip.setText(rs.getString(7));
						email.setText(rs.getString(9));
						phone.setText(rs.getString(8));
						DOB.setValue(LOCAL_DATE(rs.getString(10)));
						if(rs.getInt(12)==1) 
						yesCitizen.setSelected(true);
						else
							noCitizen.setSelected(true);
						insuranceID.setText(rs.getString(13));
						insuranceCarrier.setText(rs.getString(14));
				}
			}
			catch(Exception e) {
				System.err.println("Error in Updating TextFields!");
			}
			
			//Then, update visit table....
			try {
				System.out.println(userID);
				tableContents=FXCollections.observableArrayList();
				ResultSet rs=stmt.executeQuery("        SELECT p1.Date,p1.Reason,p1.Results,p1.VisitID, p1.patientID, p1.PhysicianID, p2.LastName,p2.FirstName,p1.followUpID,p1.Notes" + 
						"       FROM visits AS p1 INNER JOIN users AS p2 " + 
						"         ON p1.PatientID=" + userID + " AND p2.UserID=p1.PhysicianID");  
			while(rs.next()) {
				System.out.println("Prior visits.");
				tableContents.add(new Visit_Model(rs.getString(1),rs.getString(2),rs.getString(7)+"," + rs.getString(8),rs.getString(3),rs.getInt(4),userID,rs.getInt(9),con,rs.getString(10)));
			}
			}
			catch(NullPointerException e) {
				System.out.println("No new visits");
			}
			table.setItems(tableContents);
			table.refresh();

		}
		
		private static final LocalDate LOCAL_DATE (String dateString){
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		    LocalDate localDate = LocalDate.parse(dateString, formatter);
		    return localDate;
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
