-- MySQL Script generated by MySQL Workbench
-- Tue Apr  7 02:35:32 2020
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
  `LastLogin` DATE NOT NULL,
  `UserID` INT NOT NULL,
  PRIMARY KEY (`UserID`, `LastLogin`))
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
  `visitID` INT NOT NULL,
  `patientID` INT NULL,
  `primaryPhysID` INT NULL,
  `followUpID` INT NULL,
  PRIMARY KEY (`visitID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Patients` (
  `patientID` INT NOT NULL,
  PRIMARY KEY (`patientID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Tests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Tests` (
  `visitID` INT NOT NULL,
  `testTypeID` INT NOT NULL,
  `techID` INT NULL,
  PRIMARY KEY (`visitID`, `testTypeID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TestType`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TestType` (
  `testTypeID` INT NOT NULL,
  `TestType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`testTypeID`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
