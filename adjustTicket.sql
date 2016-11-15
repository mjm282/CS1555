-- You should create a trigger, called adjustTicket, that adjusts the cost of a reservation when
-- the price of one of its legs changes before the ticket is issued.

CREATE OR REPLACE TRIGGER adjustTicket
    AFTER UPDATE ON price
    FOR EACH ROW
    BEGIN

        update reservation
        set cost = cost - :old.high_price + :new.high_price
        where (reservation.start_city = :new.departure_city AND reservation.end_city = :new.arrival_city   AND reservation.ticketed = 'N') OR
              (reservation.start_city = :new.arrival_city   AND reservation.end_city = :new.departure_city AND reservation.ticketed = 'N');

    END;
/

commit;    
