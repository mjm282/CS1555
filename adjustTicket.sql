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

-- You should create a trigger, called adjustTicket, that adjusts the cost of a reservation when
-- the price of one of its legs changes before the ticket is issued.

CREATE OR REPLACE TRIGGER adjustTicket
    AFTER UPDATE ON price
    FOR EACH ROW
    BEGIN
        update reservation
        set cost = cost - :old.high_price + :new.high_price
        where (reservation.start_city = :new.departure_city AND reservation.end_city = :new.arrival_city   AND reservation.ticketed = 'N')
            OR (reservation.start_city = :new.arrival_city   AND reservation.end_city = :new.departure_city AND reservation.ticketed = 'N' AND
                isRoundTrip(reservation_number)=1)
            ;

    END;
/

commit;    
