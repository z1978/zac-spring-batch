-- -----------------------------------------------------
-- Schema cross_border_trading
-- -----------------------------------------------------
-- CREATE SCHEMA cross_border_trading DEFAULT CHARACTER SET utf8 ;
-- USE cross_border_trading ;

-- -----------------------------------------------------
-- Table people
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS people ;

CREATE TABLE people (
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NULL,
  PRIMARY KEY (first_name) );



