package com.hospital.system.maven_hospital_system;
import java.io.IOException;

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
 * MAIN APPLICATION RUNNER, STARTS WITH LOGIN, WILL REDIRECT TO ANY CONTROLLER SPECIFIED! SQL PASS: dv7&hH9#$6
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
    
}
