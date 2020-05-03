-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patients`
--

DROP TABLE IF EXISTS `patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patients` (
  `patientID` int NOT NULL,
  `lName` varchar(45) DEFAULT NULL,
  `fName` varchar(45) DEFAULT NULL,
  `Address` varchar(65) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `State` varchar(45) DEFAULT NULL,
  `Zip` varchar(6) DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Email` varchar(55) DEFAULT NULL,
  `DateOfBirth` varchar(45) DEFAULT NULL,
  `Country` varchar(45) DEFAULT NULL,
  `isResident` tinyint DEFAULT NULL,
  `insuranceID` varchar(55) DEFAULT NULL,
  `insuranceProvider` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`patientID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patients`
--

LOCK TABLES `patients` WRITE;
/*!40000 ALTER TABLE `patients` DISABLE KEYS */;
INSERT INTO `patients` VALUES (4043,'Smith','John','2 Apple Hill','Cupertino','CA','99999','3109019902','jsmith@applehill.com','2020-04-08','United States',1,'192-29439','aet'),(11421,'Appleseed','Johnny','120 Apple Hill','Hartford','CT','06110','2229102902','appleseed@johnny.com','1988-04-09','United States',1,'19038202-38930','Aet'),(20203,'John','Bob','kdskak','jkajsdf','kkjaksd','kjkdjf','kjksdfk','kksdjkfj','2015-04-03','United States',0,'jksjdkfl','kjksdjfk'),(23834,'b','a','c','d','e','f','g','h','2020-04-03','United States',1,'j','i'),(24710,'kaksdfaj','Boom','kdskak','jkajsdf','kkjaksd','kjkdjf','kjksdfk','kksdjkfj','2015-04-03','United States',0,'jksjdkfl','kjksdjfk'),(31539,'kaksdfaj','ajdfakj','kdskak','jkajsdf','kkjaksd','kjkdjf','kjksdfk','kksdjkfj','2015-04-03','Other',0,'jksjdkfl','kjksdjfk');
/*!40000 ALTER TABLE `patients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-17 23:57:49
