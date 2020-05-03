package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.*;
import java.util.ResourceBundle;



public class FXMLLoginController<E> extends App implements Initializable{
	private Stage stage;
	private Scene scene;
	private String passHash;
	private int roleNum=-1;
	private int id;
	private boolean isAdmin;
	
	private Connection con;

	
	private int numAttempts=5;
	@FXML
	private PasswordField passField;
	
	@FXML
	private TextField userField;
	@FXML
	private GridPane grid;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	/**
	 * Constructor for Login Object
	 * @param stage
	 */
	public FXMLLoginController(Stage stage){
		this.stage = stage;
		System.out.println("Login Screen...");
	}
	/**
	 * Creation of Login Screen, contains enter key handler for login...
	 * @return Current scene to be displayed
	 * @throws IOException If FXML not found...
	 */
	public Scene LoginScreen() throws IOException {
		//stage.hide();
		FXMLLoader loader = new FXMLLoader();
		//Load the FXML file
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login2.fxml"));;
		//Listener for Enter Key//
		root.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
						public void handle(KeyEvent key) {
							if(key.getCode() == KeyCode.ENTER) {
								System.out.println("Enter Pressed");
								/*
								 * At this point, we point to Login Verification with DB...
								 * New Method to be created
								 */
								try {
									passHash = toHexString(getSHA(passField.getText()));
									System.out.println(passHash);
								}
								catch(NoSuchAlgorithmException e) {
									System.err.println("Encryption error!  Please check to make sure encryption type valid...");
								}
								try {
									if(!isValidCredentials(userField.getText(),passHash)  /* CHECK CREDENTIALS HERE*/)
									{
										numAttempts--;
										if(numAttempts==0) {
											System.out.println("You exceeded the attempts, try again soon...");
											failedAttempts();
										}
									}
									//If credentials are valid...
									else {
										switchHome();
									}

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}
				}
				);
		//Instantiate scene
		this.scene = new Scene(root);
		grid = (GridPane) scene.lookup("#grid");
		passField = (PasswordField) scene.lookup("#passField");  //Use the Current scene to lookup password ID for retrieval of password.
		userField = (TextField)scene.lookup("#userField");  
		return scene;
	}

	/**
	 * TO BE IMPLEMENTED!!!  Allow to look up if user correctly inputted credentials...
	 * @param userName The current user Input for name
	 * @param passHash The Hashed Password
	 * @return If credentials are correct.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private Boolean isValidCredentials(String userName, String passHash) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		try{Class.forName("com.mysql.jdbc.Driver");}catch(Exception e) {System.out.println("FAILED jdbc driver");}
		try {
		con=DriverManager.getConnection(
		"jdbc:mysql://localhost:3306/mydb?characterEncoding=latin1&useConfigs=maxPerformance&useSSL","root","Abcdefg123");
		Statement stmt=con.createStatement();  
		//CHECK LOGIN!!!
		ResultSet rs=stmt.executeQuery("SELECT * FROM users WHERE UserID=" + userField.getText());  
		while(rs.next()) {
			if(rs.getString(5).matches(passHash)) {
				roleNum= Integer.valueOf(rs.getString(2));
				if(rs.getInt(6) ==1)
					isAdmin = true;
				else
					isAdmin=false;
				System.out.println("ROLE: " + roleNum);
				System.out.println("SUCCESS");
				return true;
				/*	When Successfully Logged in, check role number and display new screen based off of this*/
			}
			else {
				System.out.println("Failed!");
				return false;
			}
			//System.out.println(rs.getString(1) + rs.getString(2) + rs.getString(3) + rs.getString(4)+rs.getString(5));
		}
		}
		catch(Exception e) {
			System.out.println("Server Connection Exception!");
		}
		return false;
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void failedAttempts() throws InterruptedException {
		Thread.sleep(5000);
		numAttempts=5;
	}
	public int getRole() {
		return roleNum;
	}
	/**
	 * Method that will switch to correct screen based on role...
	 * @throws IOException 
	 */
	private Object switchHome() throws IOException {
		switch(roleNum) {
		case 0:{
			if(isAdmin) {
			System.out.println("Admin");
			AdministratorController admin = new AdministratorController(stage,scene,con,userField.getText());
			break;
			}
			else {
				System.out.println("Secretary");
				SecretaryController secretary = new SecretaryController(stage,scene,con);
				break;
			}
		}
		case 1:{
			System.out.println("Doctor" + Integer.valueOf(userField.getText()));
			DoctorController doctor = new DoctorController(stage,scene,con,Integer.valueOf(userField.getText()));
			break;
		}
		case 2:{
			System.out.println("Nurse");
			NurseController nurse = new NurseController(stage,scene,con,Integer.valueOf(userField.getText()));
			break;
		}
		case 3:{
			System.out.println("Lab Technician");
			
			break;
		}
		}
		return null;
		//stage.hide();
	}
	
}
