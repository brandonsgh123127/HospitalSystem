-- MySQL Script generated by MySQL Workbench
-- Sat Apr 11 13:24:07 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Users` (
  `UserID` INT NOT NULL,
  `RoleID` INT NULL,
  `FirstName` VARCHAR(45) NULL,
  `LastName` VARCHAR(45) NULL,
  `PassHash` VARCHAR(64) NULL,
  `IsAdmin` TINYINT(1) NULL,
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Roles` (
  `RoleID` INT NOT NULL,
  `Role` VARCHAR(30) NULL,
  PRIMARY KEY (`RoleID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Logins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Logins` (
  `TimeStamp` DATETIME NOT NULL,
  `UserID` INT NOT NULL,
  PRIMARY KEY (`UserID`, `TimeStamp`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sample`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sample` (
  `Name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`Name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Visits`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Visits` (
  `VisitID` INT NOT NULL,
  `Date` VARCHAR(10)NULL,
  `Reason` VARCHAR(100)NULL,
  `PatientID` INT NULL,
  `PhysicianID` INT NULL,
  `followUpID` INT NULL,
  `Results` VARCHAR(100) null,
  PRIMARY KEY (`VisitID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Patients` (
  `patientID` INT NOT NULL,
  `lName` VARCHAR(45) NULL,
  `fName` VARCHAR(45) NULL,
  `Address` VARCHAR(65) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` VARCHAR(6) NULL,
  `Phone` VARCHAR(10) NULL,
  `Email` VARCHAR(55) NULL,
  `DateOfBirth` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  `isResident` TINYINT NULL,
  `insuranceID` VARCHAR(55) NULL,
  `insuranceProvider` VARCHAR(45) NULL,
  PRIMARY KEY (`patientID`))
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `mydb`.`Tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Tests` (
  `TestID` INT NOT NULL,
  `VisitID` INT NOT NULL,
  `TestTypeID` INT NOT NULL,
  `TechID` INT NULL,
  `Result` LONGTEXT NULL,
  `ResultImg` LONGBLOB NULL,
  PRIMARY KEY (`TestID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TestType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TestType` (
  `testTypeID` INT NOT NULL,
  `TestType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`testTypeID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Insurances`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Insurances` (
  `InsuranceID` INT NOT NULL,
  `InsuranceName` VARCHAR(45) NULL,
  PRIMARY KEY (`InsuranceID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Prescriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Prescriptions` (
  `PrescriptionID` INT NOT NULL,
  `VisitID` INT NULL,
  `PrescriptionTypeID` INT NULL,
  `Dosage` VARCHAR(45) NULL,
  `Instructions` VARCHAR(45) NULL,
  PRIMARY KEY (`PrescriptionID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`PrescriptionTypes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`PrescriptionTypes` (
  `PrescriptionTypeID` INT NOT NULL,
  `PrescriptionType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PrescriptionTypeID`))
ENGINE = InnoDB
COMMENT = '	';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


show tables from mydb;
use mydb;
-- Insert Default Users, 1234 admin, 3270 root
INSERT INTO `users` VALUES (1234,0,'Andrew','Jung','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',1),(3270,0,'John','Smith','4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2',0);
