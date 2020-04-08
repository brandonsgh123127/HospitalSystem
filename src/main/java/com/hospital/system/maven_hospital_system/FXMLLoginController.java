package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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



public class FXMLLoginController{
	private Stage stage;
	private Scene scene;
	private String passHash;
	
	Connection con;

	
	private int numAttempts=5;
	@FXML
	private PasswordField passField;
	
	@FXML
	private TextField userField;
	@FXML
	private GridPane grid;
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
		String path = System.getProperty("user.dir") + "\\fxml\\Login2.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		//Load the FXML file
		Parent root = loader.load(fxmlStream);
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
	 * Hash Passwords for use within db matching...
	 * @param input password input
	 * @return SHA 256 in Byte format within array
	 * @throws NoSuchAlgorithmException If algorithm not found...
	 */
	private byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * Allow for return of string format of Bytes.
	 * @param hash SHA 256 in array
	 * @return String format of SHA 256
	 */
	private String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1,hash);
		//Converts to a hex value...
		StringBuilder hexString = new StringBuilder(number.toString(16));
		
		//leading zeros to look pretty!
		while(hexString.length() < 32)
			hexString.insert(0, '0');
		
		return hexString.toString();
		
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
		"jdbc:mysql://localhost:3306/mydb?characterEncoding=latin1&useConfigs=maxPerformance","root","Abcdefg123");
		Statement stmt=con.createStatement();  
		//CHECK LOGIN!!!
		ResultSet rs=stmt.executeQuery("SELECT * FROM users WHERE UserID=" + userField.getText());  
		while(rs.next()) {
			if(rs.getString(5).matches(passHash)) {
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
		return true;
	}
	
	/**
	 * @throws InterruptedException 
	 * 
	 */
	private void failedAttempts() throws InterruptedException {
		Thread.sleep(5000);
		numAttempts=5;
	}
	
	

}
