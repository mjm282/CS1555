--You should create a trigger called planeUpgrade, that changes the plane (type) of a flight to
--an immediately higher capacity plane (type), if it exists, when a new reservation is made on that
--flight and there are no available seats (i.e., the flight is fully booked). A change of plane will
--fail only if the currently assigned plane for the flight is the one with the biggest capacity. For
--simplicity, we assume that there are always available planes for a switch to succeed.

CREATE OR REPLACE TRIGGER planeUpgrade
	BEFORE INSERT ON Reservation_details
	FOR EACH ROW
	DECLARE
		old_plane_type	char(4);
		old_plane_capacity	int;
		seats_taken int;
		new_plane_type char(4);
	BEGIN	
		--figures out what plane is being used for this flight
		SELECT plane_type INTO old_plane_type
		FROM Flight
		WHERE :new.flight_number = Flight.flight_number;
	
		--figures out the amount of seats already filled
		SELECT COUNT(*) INTO seats_taken
		FROM Reservation_details
		WHERE :new.flight_number = Flight.flight_number;

		--gets the capacity of the old plane
		SELECT plane_capacity INTO old_plane_capacity
		FROM Plane
		WHERE :new.flight_number = Flight.flight_number;

		--if over capacity, change the plane type to the next largest
		IF (seats_taken + 1) >= old_plane_capacity THEN
			
			--all plane types with a capacity higher than the old one, sorted ascending, picks the first one
			SELECT * FROM( 
				SELECT plane_type
				FROM Plane
				GROUP BY plane_type
				HAVING plane_capacity > old_plane_capacity
				ORDER BY plane_capacity ASC;)
			INTO new_plane_type
			WHERE ROWNUM = 1;

			--updates Flight with the new plane type
			UPDATE Flight SET plane_type = new_plane_type WHERE :new.flight_number = Flight.flight_number;		
		END IF;
		END;
	/
