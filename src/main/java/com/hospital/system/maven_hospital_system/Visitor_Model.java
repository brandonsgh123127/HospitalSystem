package com.hospital.system.maven_hospital_system;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Visitor_Model{
		
		private SimpleIntegerProperty userID;

		private SimpleStringProperty fName;
		private SimpleStringProperty lName;
		private SimpleStringProperty DOB;
		private SimpleStringProperty address;
		private SimpleStringProperty city;
		private SimpleStringProperty state;
		private SimpleStringProperty zip;
		private SimpleStringProperty email;
		private SimpleStringProperty phone;
		private SimpleStringProperty country;
		private SimpleBooleanProperty isResident;
		private SimpleStringProperty insuranceID;
		private SimpleStringProperty insuranceProvider;
		
		
		public Visitor_Model(Integer id, String fName, String lName, String DOB,String address, String city, String state, String zip, String email, String phone,String country, Boolean isResident, String insuranceID,String insuranceProvider) {
			this.userID=new SimpleIntegerProperty(id);
			this.fName=new SimpleStringProperty(fName);
			this.lName = new SimpleStringProperty(lName);
			this.DOB = new SimpleStringProperty(DOB);
			this.address=new SimpleStringProperty(address);
			this.city = new SimpleStringProperty(city);
			this.state = new SimpleStringProperty(state);
			this.zip = new SimpleStringProperty(zip);
			this.email=new SimpleStringProperty(email);
			this.phone = new SimpleStringProperty(phone);
			this.country=new SimpleStringProperty(country);
			this.isResident=new SimpleBooleanProperty(isResident);
			this.insuranceID=new SimpleStringProperty(insuranceID);
			this.insuranceProvider=new SimpleStringProperty(insuranceProvider);
		}

		
		public int getUserID() {
			return userID.get();
		}
		public void setUserID(int id) {
			this.userID=new SimpleIntegerProperty(id);
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
		public String getAddress() {
			return address.get();
		}
		public void setAddress(String add) {
			this.address=new SimpleStringProperty(add);
		}
		
		public String getCity() {
			return city.get();
		}
		public void setCity(String city) {
			this.city = new SimpleStringProperty(city);
		}
		public String getState() {
			return state.get();
		}
		public void setState(String state) {
			this.state = new SimpleStringProperty(state);
		}
		public String getZip() {
			return zip.get();
		}
		public void setZip(String zip) {
			this.zip= new SimpleStringProperty(zip);
		}
		public String getPhone() {
			return phone.get();
		}
		public void setPhone(String phone) {
			this.phone = new SimpleStringProperty(phone);
		}
		public String getEmail() {
			return email.get();
		}
		public void setEmail(String email) {
			this.email = new SimpleStringProperty(email);
		}		
		public String getCountry() {
			return country.get();
		}
		public void setCountry(String country) {
			this.country = new SimpleStringProperty(country);
		}
		public boolean isResident() {
			return isResident.get();
		}
		public void setIsResident(boolean isResident) {
			this.isResident = new SimpleBooleanProperty(isResident);
		}
		public String getInsuranceID() {
			return insuranceID.get();
		}
		public void setInsuranceID(String insuranceID) {
			this.insuranceID=new SimpleStringProperty(insuranceID);
		}
		public String getInsuranceProvider() {
			return insuranceProvider.get();
		}
		public void setInsuranceProvider(String insurance)
		{
			this.insuranceProvider=new SimpleStringProperty(insurance);
		}
		
}
