package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;


public class FXMLLoginController{
	
	public FXMLLoginController(){
		System.out.println("Login Screen...");
	}
	public Scene LoginScreen(Stage stage) throws IOException {
		//stage.hide();
		FXMLLoader loader = new FXMLLoader();
		String path = System.getProperty("user.dir") + "\\fxml\\Login2.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		
		Parent root = loader.load(fxmlStream);
		
		Scene scene = new Scene(root);
		return scene;
	}
	

}
