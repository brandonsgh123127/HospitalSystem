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