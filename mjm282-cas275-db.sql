DROP TABLE Airline CASCADE CONSTRAINTS;
DROP TABLE Flight CASCADE CONSTRAINTS;
DROP TABLE Plane CASCADE CONSTRAINTS;
DROP TABLE Price CASCADE CONSTRAINTS;
DROP TABLE Customer CASCADE CONSTRAINTS;
DROP TABLE Reservation CASCADE CONSTRAINTS;
DROP TABLE Reservation_details CASCADE CONSTRAINTS;
DROP TABLE Date CASCADE CONSTRAINTS;

Commit;


CREATE TABLE Airline(
	airline_id varchar(5),
	airline_name varchar(50),
	airline_abbreviation varchar(10),
	year_founded int,
	CONSTRAINT Airline_PK PRIMARY KEY (airline_id) 
		INITIALLY DEFERRED DEFERRABLE);

CREATE TABLE Flight(
	flight_number varchar(3),
	airline_id varchar(5),
	plane_type char(4),
	departure_city varchar(3),
	arrival_city varchar(3),
	departure_time varchar(4),
	arrival_time varchar(4),
	weekly_schedule varchar(7),
	CONSTRAINT Flight_PK PRIMARY KEY (flight_number)
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Flight_FK1 FOREIGN KEY (plane_type) REFERENCES Plane.plane_type
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Flight_FK2 FOREIGN KEY (airline_id) REFERENCES Airline.airline_id
		INITIALLY DEFERRED DEFERRABLE);

CREATE TABLE Plane(
	plane_type char(4),
	manufacture varchar(10),
	plane_capacity int,
	last_service date,
	year int,
	owner_id varchar(5),
	CONSTRAINT Plane_PK PRIMARY KEY (plane_type)
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Plane_FK FOREIGN KEY (owner_id) REFERENCES Airline.airline_id
		INITIALLY DEFERRED DEFERRABLE);

CREATE TABLE Price(
	departure_city varchar(3),
	arrival_city varchar(3),
	airline_id varchar(5),
	high_price int,
	low_price int,
	CONSTRAINT Price_PK PRIMARY KEY (departure_city, arrival_city)
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Price_FK FOREIGN KEY (airline_id) REFERENCES Airline.airline_id
		INITIALLY DEFERRED DEFERRABLE);
	
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
	frequent_miles varchar(5),
	CONSTRAINT Customer_PK PRIMARY KEY (cid)
		INITIALLY DEFERRED DEFERRABLE);

CREATE TABLE Reservation(
	reservation_number varchar(5),
	cid varchar(9),
	cost int,
	credit_card_num varchar(16),
	reservation_date date,
	ticketed varchar(1),
	CONSTRAINT Reservation_PK PRIMARY KEY (reservation_number)
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Reservation_FK FOREIGN KEY (cid) REFERENCES Customer.cid
		INITIALLY DEFERRED DEFERRABLE);

CREATE TABLE Reservation_details(
	reservationi_number varchar(5),
	flight_number varchar(3),
	flight_date date,
	leg int,
	CONSTRAINT Res_details_PK PRIMARY KEY (reservation_number, leg)
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Res_details_FK1 FOREIGN KEY (reservation_number) REFERENCES Reservation.reservation_number
		INITIALLY DEFERRED DEFERRABLE,
	CONSTRAINT Res_details_FK2 FOREIGN KEY (flight_number) REFERENCES Reservation.flight_number
		INITIALLY DEFERRED DEFERRABLE);

Date(
	c_date date,
	CONSTRAINT Date_PK PRIMARY KEY (c_date)
		INITIALLY DEFERRED DEFERRABLE);
	
Commit;