package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class FXMLLoginController{
	Stage stage;
	public FXMLLoginController(Stage stage){
		this.stage = stage;
		System.out.println("Login Screen...");
	}
	public Scene LoginScreen() throws IOException {
		//stage.hide();
		FXMLLoader loader = new FXMLLoader();
		String path = System.getProperty("user.dir") + "\\fxml\\Login2.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		
		Parent root = loader.load(fxmlStream);
		root.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
						public void handle(KeyEvent key) {
							if(key.getCode() == KeyCode.ENTER) {
								System.out.println("Enter Pressed");
								/*
								 * At this point, we point to Login Verification with DB...
								 * New Method to be created
								 */
							}
						}
				});
		
		Scene scene = new Scene(root);
		return scene;
	}
	
	private void hashPass( ) {
		
	}
	

}
