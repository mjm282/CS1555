-- You should create a trigger, called adjustTicket, that adjusts the cost of a reservation when
-- the price of one of its legs changes before the ticket is issued.

CREATE OR REPLACE TRIGGER adjustTicket
    AFTER UPDATE ON price
    FOR EACH ROW
    DECLARE 
        hprice_change int;
        lprice_change int;
    BEGIN
        -- finding the price differences 
        SELECT :new.high_price - :old.high_price INTO hprice_change
        FROM dual;

        SELECT :new.low_price - :old.low_price INTO lprice_change
        FROM dual;

        -- I'm a bit iffy on when to use high vs low
        UPDATE reservation 
        
        SET cost = cost + hprice_change
        FROM reservation r
        WHERE start_city = :new.departure_city AND
              end_city   = :new.arrival_city AND
              ticketed   = 'N';
        dbms_output.putline(hprice_change);

    END;
/

commit;    
