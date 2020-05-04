# CS460W Hospital System

Welcome to a unique-simplistic software meant to simulate a hospital system!

## What is this?

A team-based project meant to simulate the software development process.  This system is built using Maven on Java 1.8.  


## What is not working

  * Minor bugs displaying Tests and Prescriptions With Doctor.


## How to Run?

  * Import project through eclipse, navigate to src/main/java/com/hospital/system/maven_hospital_system/app.java/com/hospital/system/maven_hospital_system/EditIntCell.java
  
  * On Line 65, you will see 
  ```java
  textField.setOnKeyPressed(t -> {
  ```
  * Highlight -> and it will recommend to accept compliance to jdk 1.8
  _____________________________________________________________________________
  ### Make Sure MySQL Service is running in Services.msc!
  * Under MySQL Workbench, create new user, and make the Login Name 'User'
  ** Authentication type: Standard
  ** Password by default is Abcdefg123 for logging in
  * Next, in server, go to file -> open sql script
   **Open tables.sql in db/NEWDB_SCRIPT**
   ** Execute the sql script
  * Now, in src/main/java/com/hospital/system/maven_hospital_system/app.java/com/hospital/system/maven_hospital_system/FXMLLoginController.java on line 138, change whatever is necessary to meet login needs.  
  * Run file through app.java,
  * JDBC drivers might need to be installed*

  * Make sure you have a mysql server, with user root, and password as Abcdefg123, or switch inside the FXMLLoginController.java file.

  * Create multiple types of users using default admin login.
