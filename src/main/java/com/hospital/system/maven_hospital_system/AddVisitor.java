package com.hospital.system.maven_hospital_system;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

import java.sql.Connection;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddVisitor implements Initializable{
	
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
	TableView table;
	@FXML
	TableColumn dateColumn,reasonColumn,doctorColumn,moreColumn;
	
	@FXML
	CheckBox yesCitizen,noCitizen;
	
	public AddVisitor(Stage stage, Connection con) {
		this.con= con;
		fields = new ArrayList<>();
		this.origStage = stage;
		dialog = new Stage();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		noErrors = false;
		fields.add(fName);fields.add(city);fields.add(phone);fields.add(insuranceID);
		fields.add(lName);fields.add(state);fields.add(email);
		fields.add(address);fields.add(zip);fields.add(insuranceCarrier);
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
	}
	public Stage display() {
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return dialog;
	}
	@FXML
	public void addNew() throws SQLException, NoSuchAlgorithmException{
			Visitor_Model temp;

			if(yesCitizen.isSelected()) {
				temp =new Visitor_Model(genID(),fName.getText(),lName.getText(),DOB.getValue().toString(),
 													address.getText(),city.getText(),state.getText(),zip.getText(),email.getText(),phone.getText(),"United States",true,
 													insuranceID.getText(),insuranceCarrier.getText());
			}
			else {
	 			temp =new Visitor_Model(genID(),fName.getText(),lName.getText(),DOB.getValue().toString(),
							address.getText(),city.getText(),state.getText(),zip.getText(),email.getText(),phone.getText(),"Other",false,
							insuranceID.getText(),insuranceCarrier.getText());
				
			}
			try {
				PreparedStatement stmt;
				if(temp.isResident()) {
					stmt=con.prepareStatement("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
									",'"+temp.getLName()+"', '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
									+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
									"United States"  + "' , " + "1" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
					System.out.println("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
							","+temp.getLName()+", '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
							+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
							"United States" + "' , " + "1" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
				}
				else {
					stmt=con.prepareStatement("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
							","+temp.getLName()+", '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
							+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
							"Other" + "' , " + "0" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
			System.out.println("INSERT INTO `Patients` VALUES ("+temp.getUserID()+
					","+temp.getLName()+", '"+temp.getFName()+"' , '"+temp.getAddress()+"', '" + temp.getCity()+ "' , '"
					+ temp.getState() + "' , '" + temp.getZip() + "' , '"+ temp.getPhone()  + "' , '" + temp.getEmail()+ "' , '" + temp.getDOB()+ "' , '" + 
					"Other" + "' , " + "0" + " , '"+ temp.getInsuranceID() + "' , '" + temp.getInsuranceProvider() + "')");
					
				}
					stmt.execute();
			}
			catch(SQLException e) {
				System.out.println("Updating Entry..." +temp.getUserID());
				PreparedStatement stmt=con.prepareStatement("UPDATE `Patients` SET lName = '" + temp.getFName() + "',lName = '" + temp.getLName() + "', Address= '"
															+temp.getAddress()+"' , City= '" + temp.getCity() + "' , State= '" + temp.getState() + "' , Zip = '" +
															temp.getZip() + "' , Phone = '" + temp.getPhone() + "' , Email = '" + temp.getEmail() + "' , Country = " +
															"'United States" + "' , insuranceID = '" + temp.getInsuranceID() + "' , insuranceProvider = '" + temp.getInsuranceProvider()
															+ "' where UserID = "+temp.getUserID());
				stmt.execute();
				}
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

}
