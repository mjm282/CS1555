--CS1555 Project Phase 1 Schema
--Matthew Melchert: mjm282
--Charles Mith: cas275


--Drop all tables
DROP TABLE Airline CASCADE CONSTRAINTS;
DROP TABLE Flight CASCADE CONSTRAINTS;
DROP TABLE Plane CASCADE CONSTRAINTS;
DROP TABLE Price CASCADE CONSTRAINTS;
DROP TABLE Customer CASCADE CONSTRAINTS;
DROP TABLE Reservation CASCADE CONSTRAINTS;
DROP TABLE Reservation_details CASCADE CONSTRAINTS;
DROP TABLE System_Date CASCADE CONSTRAINTS;

Commit;

--Create all tables without constraints
CREATE TABLE Airline(
	airline_id varchar(5),
	airline_name varchar(50),
	airline_abbreviation varchar(10),
	year_founded int);

CREATE TABLE Flight(
	flight_number varchar(3),
	airline_id varchar(5),
	plane_type char(4),
	departure_city varchar(3),
	arrival_city varchar(3),
	departure_time varchar(4),
	arrival_time varchar(4),
	weekly_schedule varchar(7));

CREATE TABLE Plane(
	plane_type char(4),
	manufacture varchar(10),
	plane_capacity int,
	last_service date,
	year int,
	owner_id varchar(5));

CREATE TABLE Price(
	departure_city varchar(3),
	arrival_city varchar(3),
	airline_id varchar(5),
	high_price int,
	low_price int);
	
CREATE TABLE Customer(
	cid varchar(9),
	salutation varchar(3),
	first_name varchar(30),
	last_name varchar(30),
	credit_card_num varchar(16),
	credit_card_expire date,
	street varchar(30),
	city varchar(30),
	state varchar(2),
	phone varchar(10),
	email varchar(30),
	frequent_miles varchar(5));

CREATE TABLE Reservation(
	reservation_number varchar(5),
	cid varchar(9),
	cost int,
	credit_card_num varchar(16),
	reservation_date date,
	start_city varchar(3),
	end_city varchar(3),
	ticketed varchar(1));

CREATE TABLE Reservation_details(
	reservation_number varchar(5),
	flight_number varchar(3),
	flight_date date,
	leg int);

CREATE TABLE System_Date(
	c_date date);	
	
Commit;

ALTER TABLE Airline ADD
	CONSTRAINT Airline_PK PRIMARY KEY (airline_id) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Plane ADD
	CONSTRAINT Plane_PK PRIMARY KEY (plane_type, owner_id) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Plane ADD
	CONSTRAINT Plane_FK FOREIGN KEY (owner_id) REFERENCES Airline (airline_id) INITIALLY DEFERRED DEFERRABLE;
	
ALTER TABLE Flight ADD
	CONSTRAINT Flight_PK PRIMARY KEY (flight_number) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Flight ADD
	CONSTRAINT Flight_FK1 FOREIGN KEY (plane_type, airline_id) REFERENCES Plane (plane_type, owner_id) INITIALLY DEFERRED DEFERRABLE;

--ALTER TABLE Flight ADD	
--	CONSTRAINT Flight_FK2 FOREIGN KEY (airline_id) REFERENCES Airline (airline_id) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Price ADD
	CONSTRAINT Price_PK PRIMARY KEY (departure_city, arrival_city) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Price ADD
	CONSTRAINT Price_FK FOREIGN KEY (airline_id) REFERENCES Airline (airline_id) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Customer ADD
	CONSTRAINT Customer_PK PRIMARY KEY (cid) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Reservation ADD
	CONSTRAINT Reservation_PK PRIMARY KEY (reservation_number) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Reservation ADD
	CONSTRAINT Reservation_FK FOREIGN KEY (cid) REFERENCES Customer (cid) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Reservation_details ADD
	CONSTRAINT Res_details_PK PRIMARY KEY (reservation_number, leg) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Reservation_details ADD
	CONSTRAINT Res_details_FK1 FOREIGN KEY (reservation_number) REFERENCES Reservation (reservation_number) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE Reservation_details ADD
	CONSTRAINT Res_details_FK2 FOREIGN KEY (flight_number) REFERENCES Flight (flight_number) INITIALLY DEFERRED DEFERRABLE;

ALTER TABLE System_Date ADD
	CONSTRAINT Date_PK PRIMARY KEY (c_date) INITIALLY DEFERRED DEFERRABLE;
	
Commit;	
