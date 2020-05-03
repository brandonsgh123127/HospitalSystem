package com.hospital.system.maven_hospital_system;

import java.sql.Connection;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test Viewer for Lab-Technician
 * @author spada
 *
 */
public class TestEdit_Controller {
	private Connection con;
	private Stage stage,testStage;
	private String testID;

	
	public TestEdit_Controller(Stage stage2, Connection con, String string) {
		this.stage=stage2;
		this.con=con;
		this.testID=string;
	}


	public Stage getDisplay() {
		// TODO Auto-generated method stub
		return null;
	}

}
