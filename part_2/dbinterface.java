import java.sql.*;
import java.io.*;
public class dbinterface{

    private static Connection connection;
    private static String uname, passwrd;
    private static final String USER_MENU = "1: Add customer\n" + 
        "2: Show customer info, given customer name\n" +
        "3: Find price for flights between two cities\n" +
        "4: Find all routes between two cities\n" +
        "5: Find all routes between two cities of a given airline\n" +
        "6: Find all routes with available seats between two cities on given day\n" +
        "7: For a given airline, find all routes with available seats between two cities on given day\n" +
        "8: Add reservation\n" +
        "9: Show reservation info, given reservation number\n" +
        "0: Buy ticket from existing reservation\n" +
        "q: Quit";

    private static final String ADMIN_MENU = "1: Erase the database\n" +
        "2: Load airline information\n" +
        "3: Load schedule information\n" +
        "4: Load pricing information\n" +
        "5: Load plane information\n" +
        "6: Generate passenger manifest for specific flight on given day\n" +
        "q: Quit";

    public static void main(String[] args){
        System.out.println("hello world");


        // change this if you're debuging 
        uname = "cas275";
        passwrd = "pitt1787";

        try{
            System.out.println("Connecting . . .");
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
            connection = DriverManager.getConnection(url, uname, passwrd);
        }
        catch(Exception e){
            System.out.println("error connecting + " + e);
            e.printStackTrace();
        }

        System.out.println("are you an admin? y/n");
        char in = '0';
        try{
            in = (char) System.in.read();
        }
        catch(Exception e2){
            System.out.println("couldn't read that " + e2);
        }

        if(in == 'y'){
            adminInterface();
        }
        else{
            userInterface();
        }

        //close the connection
        System.out.println("closing connection to the db");
        try{
            connection.close();
        }
        catch(Exception e5){
            System.out.println("could not close");
        }
    }


    public static void adminInterface(){
        
        System.out.println("~Admin menu~");
        System.out.println(ADMIN_MENU);
        
        char in = 'z';
        
        try{
            in = (char) System.in.read();
            while(in == '\n'){
                in = (char) System.in.read();
            }
        }
        catch(Exception e){
            System.out.println("could not read");
        }

        while(in!='q'){
            if(in == '1'){

            }
            else if(in == '2'){

            }
            else if(in == '3'){

            }
            else if(in == '4'){

            }
            else if(in == '5'){

            }
            else if(in == '6'){

            }
            else if(in != 'q'){
                System.out.println("invalid");
            }

            System.out.println(ADMIN_MENU);
            try{
                in = (char) System.in.read();
                while(in == '\n'){
                    in = (char) System.in.read();
                }
            }
            catch(Exception e1){
                System.out.println("could not read");
                in = 'z';
            }

        }
        
    }


    public static void userInterface(){
        Scanner scan = new Scanner(System.in);
        System.out.println("User menu");
        System.out.println(USER_MENU);

        char in = 'z';
        try{
            in = (char) System.in.read();
            while(in == '\n'){
                in = (char) System.in.read();
            }
        }
        catch(Exception e1){
            System.out.println("read error" + e1);
        }
        while(in != 'q'){
            if(in == '1'){
                System.out.println("Create New User");
                System.out.print("Please enter a salutation: ");
                String salutation = scan.next();
                System.out.print("Please enter first name: ");
                String fname = scan.next();
                System.out.print("Please enter last name: ");
                String lname = scan.next();
                System.out.print("Please enter street name: ");
                String street = scan.next();
                System.out.print("Please enter city name: ");
                String city = scan.next();
                System.out.print("Please enter state abbreviation: ");
                String state = scan.next();
                System.out.print("Please enter phone number: ");
                String pn = scan.next();
                System.out.print("Please enter email address: ");
                String email = scan.next();
                System.out.print("Please enter credit card number: ");
                String cc = scan.next();
                System.out.print("Please enter card expiration date: ");
                String expdate = scan.next();

                String findCust = "SELECT * FROM Customer WHERE first_name = ? AND last_name = ?"
                PreparedStatement checkcust = connection.prepareStatement(findCust);
                checkcust.setString(1,fname);
                checkcust.setString(2,lname);
                ResultSet rs = checkcust.executeQuery();
                if (!rs.next()){
                    print("Sorry, that user already exists in the system.");

                }
            }
            else if(in == '2'){

            }
            else if(in == '3'){

            }
            else if(in == '4'){

            }
            else if(in == '5'){

            }
            else if(in == '6'){

            }
            else if(in == '7'){

            }
            else if(in == '8'){

            }
            else if(in == '9'){

            }
            else if(in == '0'){

            }
            else if(in != 'q'){
                System.out.println("invalid");
            }
            //read input
            System.out.println(USER_MENU);
            try{
                in = (char) System.in.read();
                while(in == '\n'){
                    in = (char) System.in.read();
                }
            }
            catch(Exception e3){
                System.out.println("cannot read");
                in = 'z';
            }
            
        }
        System.out.println("quitting");

    }
}

