package com.hospital.system.maven_hospital_system;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Staff_Model {
	
	private SimpleIntegerProperty userID;
	private SimpleIntegerProperty userRole;

	private SimpleStringProperty fName;
	private SimpleStringProperty lName;
	private SimpleStringProperty pass;
	
	/*
	 * Model to Construct new Staff
	 */
	public Staff_Model(Integer id, Integer role, String fName, String lName,String pass) {
		this.userID=new SimpleIntegerProperty(id);
		this.userRole=new SimpleIntegerProperty(role);
		this.fName=new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
		this.pass=new SimpleStringProperty(pass);
	}

	
	public int getUserID() {
		return userID.get();
	}
	public void setUserID(int id) {
		this.userID=new SimpleIntegerProperty(id);
	}
	public int getUserRole() {
		return userRole.get();
	}
	public void setUserRole(Integer role) {
		this.userRole=new SimpleIntegerProperty(role);
	}
	public String getFName() {
		return fName.get();
	}
	public void setFName(String fName) {
		this.fName=new SimpleStringProperty(fName);
	}
	
	public String getLName() {
		return lName.get();
	}
	public void setLName(String lName) {
		this.lName=new SimpleStringProperty(lName);
	}
	public String getPass() {
		return pass.get();
	}
	public void setPass(String pass) {
		try {
		this.pass=new SimpleStringProperty(toHexString(getSHA(pass)));
		}
		catch(NoSuchAlgorithmException e) {
			System.out.println("No such encryption algorithm for staff");
		}
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
