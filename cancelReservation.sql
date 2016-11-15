-- You should write a trigger, called cancelReservation, that cancels (deletes) all non-ticketed
-- reservations for a flight, 12 hours prior the flight (i.e., 12 hours before the flight is scheduled to
-- depart) and if the number of ticketed passengers fits in a smaller capacity plane, then the plane
-- for that flight should be switched to the smaller-capacity plane.

CREATE OR REPLACE TRIGGER cancelReservation
	AFTER UPDATE ON System_Date
	FOR EACH ROW
	DECLARE
		flights_soon int;
	BEGIN
		--check reservation_details for flights that depart within 12 hours
		SELECT COUNT(*) INTO flights_soon
		FROM Reservation_details
		WHERE (24 * (:new.c_date - flight_date)) <= 12;
		
		IF flights_soon > 0 THEN
			--find reservations that fall under those flights and check if they are ticketed
			--check if reservations are ticketed
			--if not ticketed delete the rows
			DELETE FROM Reservation
			WHERE reservation_number IN (
				SELECT reservation_number
				FROM Reservation_details
				WHERE (24 * (:new.c_date - flight_date)) <= 12)
			AND ticketed = 'N';
			
			DELETE FROM Reservation_details
			WHERE NOT EXISTS(
				SELECT reservation_number
				FROM Reservation
				WHERE reservation_number = Reservation_details.reservation_number);
		END IF;
	END;
	/

CREATE OR REPLACE TRIGGER planeDowngrade
	AFTER DELETE ON Reservation_details
	FOR EACH ROW
	DECLARE
		old_plane_type	char(4);
		old_plane_capacity	int;
		seats_taken int;
		new_potential_plane char(4);
		new_potential_capacity int;
	BEGIN
		--finds plane type being used for this flight
		SELECT plane_type INTO old_plane_type
		FROM Flight
		WHERE :old.flight_number = flight_number;
		
		--finds amount of seats filled
		SELECT COUNT(*) INTO seats_taken
		FROM Reservation_details, Flight
		WHERE :old.flight_number = Flight.flight_number;
		
		--gets capacity of old plane
		SELECT plane_capacity INTO old_plane_capacity
		FROM (
			SELECT flight_number, plane_capacity
			FROM Flight, Plane
			WHERE Flight.plane_type = Plane.plane_type)
		WHERE :old.flight_number = flight_number;
		
		--checks for a lower capacity plane that will fit the passengers
		SELECT * INTO new_potential_plane
		FROM(
			SELECT plane_type
			FROM Plane
			WHERE plane_capacity < old_plane_capacity
			ORDER BY plane_capacity DESC)
		WHERE ROWNUM = 1;
		
		--finds the capacity of the new potential plane
		SELECT plane_capacity INTO new_potential_capacity
		FROM Plane
		WHERE plane_type = new_potential_plane;
		
		IF seats_taken <= new_potential_capacity THEN
			UPDATE Flight SET plane_type = new_potential_plane
			WHERE :old.flight_number = Flight.flight_number;
		END IF;
	END;
	/