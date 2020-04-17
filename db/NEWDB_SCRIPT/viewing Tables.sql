show tables from mydb;
use mydb;
/*update users set RoleID = 0 where UserID = 1235;*/
/*INSERT INTO `users` VALUES (1234,1,'Andrew','Jung','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',0),(1235,0,'John','Smith','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2',1);*/
select * from tests;
/*INSERT INTO visits VALUES(1,'04-15-2015','Back',40403,1234,2,'Skoliosis');*/
/*INSERT INTO tests VALUES(1,1,1,1333,'Negative',LOAD_FILE("E:\Users\i-pod\Documents\CS Work\CS460W\HospitalSystem\src\main\resources\image.jpg"));*/
SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg,p3.TestType
       FROM tests AS p1 INNER JOIN visits AS p2 inner join testtype as p3
         ON p1.VisitID= 1 AND p2.VisitID=1 and
         p1.testTypeID = p3.TestTypeID;
         /*genTable*/
        SELECT p1.VisitID, p1.patientID, p1.PhysicianID, p1.Results, p2.lName,p2.fName
       FROM visits AS p1 INNER JOIN patients AS p2 
         ON p1.patientID= 4043 AND p2.patientID=4043 ;
select * from prescriptiontypes;
select * from roles;
select * from Patients;
INSERT INTO patients VALUES(4043,'Smith','John','2 Apple Hill','Cupertino','CA','99999','3109019902','smith@applehill.com','2020-04-08','United States',1,'192-29439','aet');
select * from roles;
INSERT INTO roles VALUES(0,'Staff'),(2,'Doctor'),(3,'Nurse'),(4,'Patient');
SELECT p1.PrescriptionID, p1.VisitID, p1.PrescriptionTypeID, p1.Dosage, p1.Instructions, p3.PrescriptionType,p2.Date
FROM prescriptions AS p1 INNER JOIN visits AS p2 INNER JOIN prescriptiontypes as p3
ON p1.VisitID= p2.visitID   and p1.PrescriptionTypeID = p3.PrescriptionTypeID;
INSERT INTO prescriptiontypes VALUES(1,'Depressant'),(2,'Stimulant'),(3,'Opiod');