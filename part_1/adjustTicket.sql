CREATE OR REPLACE FUNCTION isRoundTrip(resnum in varchar) return number
is
first varchar(3);
last varchar(3);
BEGIN
    SELECT departure_city into first
    FROM reservation_details rd JOIN flight f
    ON rd.flight_number = f.flight_number
    WHERE rd.leg = 0
    AND rd.reservation_number = resnum;
    
    SELECT arrival_city into last
    FROM reservation_details rd JOIN flight f
    ON rd.flight_number = f.flight_number
    WHERE rd.reservation_number = resnum AND
    rd.leg = (select max(leg) from reservation_details r WHERE r.reservation_number = resnum);
    IF first = last
    THEN return(1);
    ELSE return(0);
    END IF;
END;
/

CREATE OR REPLACE FUNCTION isHighPrice(resnum in varchar) return number
is 
first varchar(8);
last  varchar(8);
BEGIN
    SELECT to_char(flight_date, 'MMDDYYYY') into first
    FROM reservation_details rd
    WHERE rd.reservation_number = resnum AND
          leg = 0;

    SELECT to_char(flight_date, 'MMDDYYY') into last
    FROM reservation_details rd
    WHERE rd.reservation_number = resnum AND
          rd.leg =(select max(leg) from reservation_details r WHERE r.reservation_number = resnum);

    IF first = last
    THEN return(1);
    ELSE return(0);
    END IF;
END;
/


CREATE OR REPLACE FUNCTION firstAirline(resnum in varchar) return varchar
is
    air varchar(5);
BEGIN
    SELECT airline_id INTO air
    FROM reservation_details rd JOIN flight f ON rd.flight_number = f.flight_number
    WHERE rd.reservation_number = resnum AND
    leg = 0;
    return(air);
END;
/

CREATE OR REPLACE FUNCTION lastAirline(resnum in varchar) return varchar
is
    air varchar(5);
BEGIN
    SELECT airline_id INTO air
    FROM reservation_details rd JOIN flight f ON rd.flight_number = f.flight_number
    WHERE rd.reservation_number = resnum AND
    leg = (SELECT max(leg) from reservation_details r2 WHERE r2.reservation_number = resnum);
    return(air);
END;
/


-- You should create a trigger, called adjustTicket, that adjusts the cost of a reservation when
-- the price of one of its legs changes before the ticket is issued.

CREATE OR REPLACE TRIGGER adjustTicket
    AFTER UPDATE ON price
    FOR EACH ROW
    BEGIN
        update reservation
        set cost =  CASE WHEN isHighPrice(reservation_number) = 1 
                    THEN
                        cost - :old.high_price + :new.high_price
                    ELSE 
                        cost - :old.low_price + :new.low_price
                    END
        where (reservation.start_city = :new.departure_city AND reservation.end_city = :new.arrival_city   AND reservation.ticketed = 'N' AND 
                firstAirline(reservation_number) = :new.airline_id)
            OR (reservation.start_city = :new.arrival_city   AND reservation.end_city = :new.departure_city AND reservation.ticketed = 'N' AND
                isRoundTrip(reservation_number)=1 AND lastAirline(reservation_number)= :new.airline_id)
            ;

    END;
/

commit;    
