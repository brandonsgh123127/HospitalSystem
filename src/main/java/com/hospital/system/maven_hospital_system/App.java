package com.hospital.system.maven_hospital_system;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * MAIN APPLICATION RUNNER, STARTS WITH LOGIN, WILL REDIRECT TO ANY CONTROLLER SPECIFIED!
 *
 */
public class App extends Application
{
	Scene scene;
	Stage stage;
    public static void main( String[] args )
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
    	//Start with login...
    	this.stage=stage;
    	FXMLLoginController f = new FXMLLoginController(stage);
    	try{
    		scene = f.LoginScreen();
    		this.stage.setScene(f.LoginScreen());
    	}
		catch(IOException e) {
			System.err.println("Scene Invalid!  Please Validate correct pointer to login file...");
		}
    	stage.show();
    	}
    
	/**
	 * Hash Passwords for use within db matching...
	 * @param input password input
	 * @return SHA 256 in Byte format within array
	 * @throws NoSuchAlgorithmException If algorithm not found...
	 */
	protected byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * Allow for return of string format of Bytes.
	 * @param hash SHA 256 in array
	 * @return String format of SHA 256
	 */
	protected String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1,hash);
		//Converts to a hex value...
		StringBuilder hexString = new StringBuilder(number.toString(16));
		
		//leading zeros to look pretty!
		while(hexString.length() < 32)
			hexString.insert(0, '0');
		
		return hexString.toString();
		
	}
	
    
}
