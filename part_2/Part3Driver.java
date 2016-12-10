// CS1555 Term Project
// FINAL MILESTONE (YAY!)
//
// group 13

import java.io.*;

public class Part3Driver{

    // Strings of input files
    private static final String loadAirlineFile = "";
    private static final String loadFlightFile  = "";
    private static final String loadPlaneFile   = "";
    private static final String loadPrice       = "";

    public static void main(String[] args){

        // Connect to the DB
        dbinterface.connectToDB();

        // Call admin1 (erase the DB)
        // COMMENTED OUT FOR TESTS, THIS IS DANGEROUS IF YOU'RE NOT READY FOR IT
        // dbinterface.deleteTables(); 

        // Call admin2 (load airline information)
        dbinterface.importAirlines(loadAirlineFile);

        // Call admin3 (load schedule)
        dbinterface.importFlights(loadFlightFile);

        // Call admin4 (load pricing)
        dbinterface.importPrice(loadPrice);

        // Call admin5 (load plane information)
        dbinterface.importPlanes(loadPlaneFile);

        // Let's not call admin 6 just yet, as there aren't any passengers! 
        // Call user1 (add customer)
        // Let's do 50-100 loops

        for(int i=0; i<50; i++){
            // we can just use a toString of i to make names, we don't really care who our custs are
            // let's make up an evenly distributed salutation
            String salutation;
            if(i%3 == 0){
                salutation = "Mr";
            } else if(i%3 == 2){
                salutation = "Mrs";
            }
            else{
                salutation = "Ms";
            }

            // let's make numerical based name
            String fname = (new Integer(i)).toString();
            String lname = fname;

            // yes, the credit card number is 1
            String cc = "1";
            
            // wow, everyone's credit cards have expired . . .
            String expdate = "01-MAY-01";   

            // huh, everyone lives at a different house on the same street
            String street = fname + " " + "first street";

            // ewww, cleveland
            String city;
            String state;
            if(i%2 == 0){
                city = "Pittsburgh";
                state = "PA";
            } else{
                city = "Cleveland";
                state = "OH";
            }

            // that phone number looks fake...
            String pn = "5555551234";

            // looks like everyone has their own email domain.  Cool
            String email = fname + "@" + lname + ".com";

            // ok, enough fun, let's add them back to the DB
            dbinterface.insertUserQuery(salutation,fname,lname,cc,expdate,street,city,state,pn,email);

        }

        // Call user2 (show cust, given name)
        // Let's find 20 of the ones we just added

        for(int i=0; i<20; i++){
            System.out.println("finding customer " + i);
            if(dbinterface.findUserQuery((new Integer(i)).toString(), (new Integer(i)).toString())){
                System.out.println("found");
            } else{
                System.out.println("[ERROR] could not find user " + i);
            }
        }

        // Call user3 (find flights between 2 cities)
        // This one is harder to loop, unless our city names are number that increment by 1
        // We should do this one 10+ times
        // and should print expected result too


        // Call user4 (find all routes between 2 cities)
        // Very similar to the one above 
        // once again 10+
        // and print the expected result too


        // Call user5 (find all routes between 2 cities for a given airline)
        // Let's call this one twice for each airline


        // Call user6 (find all routes with seats on a given day)
        // Let's call this 10 time now, with no reservations, and call again later


        // Call user7 (given airline, routes with seats on a day)
        // Same as above


        // Call user8 (add reservation)
        // Like 20+ reservations!


        // Call user 9 (show reservation infor)
        // to verify above 


        // Call user6 (find all routes w/ seats) again
        // line 5 times, on flights that have people on them now
        

        // Call user7 (find all routes w/ seats, airline) again
        // like 5 times, on flights that have people on them now


        // Call user0 (buy ticket for reservation)
        // call for /Most/ of the reservations


        // Call admin6 (passenger manifest)
        // finally we have data


        // -----PUT ANY OTHER TESTS BELOW HERE------




        // Close connection   
        dbinterface.closeDB();

    }


}
