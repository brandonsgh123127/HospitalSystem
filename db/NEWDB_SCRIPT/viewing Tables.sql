show tables from mydb;
use mydb;
/*update users set RoleID = 0 where UserID = 1235;*/
/*INSERT INTO `users` VALUES (1234,1,'Andrew','Jung','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',0),(1235,0,'John','Smith','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2',1);*/
select * from tests;
select * from roles;
/*INSERT INTO visits VALUES(1,'04-15-2015','Back',40403,1234,2,'Skoliosis');*/
/*INSERT INTO tests VALUES(1,1,1,1333,'Negative',LOAD_FILE("E:\Users\i-pod\Documents\CS Work\CS460W\HospitalSystem\src\main\resources\image.jpg"));*/
SELECT p1.VisitID, p1.TestID, p1.TestTypeID, p1.Result, p1.ResultImg,p3.TestType
       FROM tests AS p1 INNER JOIN visits AS p2 inner join testtype as p3
         ON p1.VisitID= 1 AND p2.VisitID=1 and
         p1.testTypeID = p3.TestTypeID;
         /*genTable*/
        SELECT  p1.VisitID, p1.patientID, p1.PhysicianID, p1.Results, p2.lName,p2.fName,p2.DateOfBirth
       FROM visits AS p1 INNER JOIN patients AS p2 
         ON p1.patientID= 4043 AND p2.patientID=4043 AND p1.visitID = 1;
select * from prescriptions;
select * from testType;
select * from tests;
select * from users;
INSERT INTO visits VALUES(3,'04-25-2021','Physical',4043,-1,4,'-');
SELECT p1.PrescriptionID, p1.VisitID, p1.PrescriptionTypeID, p1.Dosage, p1.Instructions,p2.Date
FROM prescriptions AS p1 INNER JOIN visits AS p2
ON p1.VisitID= p2.visitID;
SELECT p1.VisitID, p1.followUpID,p1.Date,p1.patientID, p1.PhysicianID,p2.lName,p2.fName,p2.DateOfBirth FROM visits AS p1 INNER JOIN patients AS p2
 ON p1.PhysicianID=2808 and p1.patientID=p2.patientID;
SELECT * FROM Visits  WHERE patientID = 4043 ORDER BY Date DESC LIMIT 1;
DELETE from visits where visitId=1808;
INSERT INTO Visits VALUES(2381,'2045-10-10' , '-' , 23834,-1, 140, '-' );
select * from patients;
SELECT * FROM visits;
delete from tests;
drop table tests;