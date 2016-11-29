import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class dbinterface{

    private static Connection connection;
    private static String uname, passwrd;
	private static String query; //currently being used query
	private static Statement statement;
	private static PreparedStatement prepStatement;
	private static ResultSet resultSet;
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

// private static final String ADMIN_MENU = "1: Erase the database\n" +
        // "2: Load airline information\n" +
        // "3: Load schedule information\n" +
        // "4: Load pricing information\n" +
        // "5: Load plane information\n" +
        // "6: Generate passenger manifest for specific flight on given day\n" +
        // "q: Quit";
	
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
			Scanner adminScan = new Scanner(System.in);
            if(in == '1')
			{
				try{
				String[] tables = {"Airline", "Flight", "Plane", "Price", "Customer", "Reservation", "Reservation_details"};
				statement = connection.createStatement();
				String deleteQuery = "DELETE FROM ";
				for(int i = 0; i < tables.length; i++)
				{
					statement.executeQuery(deleteQuery + tables[i]);
				}
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				
            }
            else if(in == '2') //insert airline data
			{
				System.out.println("Please enter full path to airline data file");
				String airFile = adminScan.next();
				query = "INSERT INTO Airline VALUES (?,?,?,?)";
				String line;
				String[] lineSplit;
				try{
					BufferedReader read = new BufferedReader(new FileReader(airFile));
					prepStatement = connection.prepareStatement(query);
					while((line = read.readLine()) != null)
					{
						lineSplit = line.split(" ");
						prepStatement.setString(1, lineSplit[0]);
						prepStatement.setString(2, lineSplit[1]);
						prepStatement.setString(3, lineSplit[2]);
						prepStatement.setInt(4, Integer.parseInt(lineSplit[3]));
						
						prepStatement.executeUpdate();
					}
				}catch (Exception e){
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
            }
            else if(in == '3') //insert flight data
			{
				System.out.println("Please enter full path to scheduling data file");
				String airFile = adminScan.next();
				query = "INSERT INTO Flight VALUES (?,?,?,?,?,?,?,?)";
				String line;
				String[] lineSplit;
				try{
					BufferedReader read = new BufferedReader(new FileReader(airFile));
					while((line = read.readLine()) != null)
					{
						lineSplit = line.split(" ");
						prepStatement = connection.prepareStatement(query);
						prepStatement.setString(1, lineSplit[0]);
						prepStatement.setString(2, lineSplit[1]);
						prepStatement.setString(3, lineSplit[2]);
						prepStatement.setString(3, lineSplit[3]);
						prepStatement.setString(4, lineSplit[4]);
						prepStatement.setString(5, lineSplit[5]);
						prepStatement.setString(6, lineSplit[6]);
						prepStatement.setString(7, lineSplit[7]);
						prepStatement.setString(8, lineSplit[8]);
						
						prepStatement.executeUpdate();
					}
				}catch (Exception e){
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
            }
            else if(in == '4')
			{

            }
            else if(in == '5') //insert plane data
			{
				System.out.println("Please enter full path to plane data file");
				String airFile = adminScan.next();
				query = "INSERT INTO Plane VALUES (?,?,?,?,?,?)";
				String line;
				String[] lineSplit;
				
				java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM/DD/YYYY");
				
				try{
					BufferedReader read = new BufferedReader(new FileReader(airFile));
					while((line = read.readLine()) != null)
					{
						lineSplit = line.split(" ");
						
						prepStatement = connection.prepareStatement(query);
						prepStatement.setString(1, lineSplit[0]);
						prepStatement.setString(2, lineSplit[1]);
						prepStatement.setInt(3, Integer.parseInt(lineSplit[2]));
						java.sql.Date date = new java.sql.Date (df.parse(lineSplit[3]).getTime());
						prepStatement.setDate(4, date);
						prepStatement.setInt(5, Integer.parseInt(lineSplit[4]));
						prepStatement.setString(6, lineSplit[5]);
						
						prepStatement.executeUpdate();
					}
				}catch (Exception e){
					System.out.println("Error: " + e.getMessage());
					e.printStackTrace();
				}
            }
            else if(in == '6')
			{

            }
            else if(in != 'q')
			{
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
        Scanner scan;
        scan = new Scanner(System.in);
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
                try {
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
                    
                    String findCust = "SELECT * FROM Customer WHERE first_name = ? AND last_name = ?";
                    PreparedStatement checkcust = connection.prepareStatement(findCust);
                    checkcust.setString(1,fname);
                    checkcust.setString(2,lname);
                    ResultSet rs = checkcust.executeQuery();
                    if (!rs.next()){
                        System.out.println("Sorry, that user already exists in the system.");
                        
                    }
                    else{
                        
                        String insCust = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement putCust = connection.prepareStatement(insCust);
                        Random rand = new Random();
                        int n = rand.nextInt(999999999) + 100000000;
                        String cid = Integer.toString(n);
                        putCust.setString(1, cid);
                        putCust.setString(2, salutation);
                        putCust.setString(3, fname);
                        putCust.setString(4, lname);
                        putCust.setString(5, street);
                        putCust.setString(6, city);
                        putCust.setString(7, state);
                        putCust.setString(8, pn);
                        putCust.setString(9, email);
                        putCust.setString(10, cc);
                        putCust.setString(11, expdate);
                        putCust.executeUpdate();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(dbinterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(in == '2'){

                try {
                    System.out.println("Find User Information");
                    System.out.print("Please enter first name: ");
                    String fname = scan.next();
                    System.out.print("Please enter last name: ");
                    String lname = scan.next();
                    String findCust = "SELECT * FROM Customer WHERE first_name = ? AND last_name = ?";
                    PreparedStatement checkcust = connection.prepareStatement(findCust);
                    checkcust.setString(1, fname);
                    checkcust.setString(2, lname);
                    ResultSet rs = checkcust.executeQuery();
                    if (rs.next()){
                        System.out.println("We should complete this function.");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(dbinterface.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
            else if(in == '3'){
                try {
                    System.out.println("Find Price Information");
                    System.out.print("Please enter origin city: ");
                    String origin = scan.next();
                    System.out.print("Please enter destination city: ");
                    String dest = scan.next();
                    String findprice = "SELECT high_price, low_price FROM Price WHERE departure_city = ? AND arrival_city = ?";
                    PreparedStatement getPrice = connection.prepareStatement(findprice);
                    getPrice.setString(1, origin);
                    getPrice.setString(2, dest);
                    ResultSet rs = getPrice.executeQuery();
                    String high_price_to = "";
                    String high_price_from = "";
                    String low_price_to = "";
                    String low_price_from = "";
                    String output = "";
                    while(rs.next()){
                        high_price_to = rs.getString("high_price");
                        low_price_to = rs.getString("low_price");
                        output = String.format("The high cost from %s to %s is %s", origin, dest, high_price_to);
                        System.out.println(output);
                        output = String.format("The low cost from %s to %s is %s", origin, dest, low_price_to);
                    }
                    getPrice = connection.prepareStatement(findprice);
                    getPrice.setString(1, dest);
                    getPrice.setString(2, origin);
                    rs = getPrice.executeQuery();
                    while(rs.next()){
                        high_price_from = rs.getString("high_price");
                        low_price_from = rs.getString("low_price");
                        output = String.format("The high cost from %s to %s is %s", dest, origin, high_price_from);
                        System.out.println(output);
                        output = String.format("The low cost from %s to %s is %s", dest, origin, low_price_from);
                    }
                    int high_round = Integer.valueOf(high_price_to) + Integer.valueOf(high_price_from);
                    int low_round = Integer.valueOf(low_price_to) + Integer.valueOf(low_price_from);
                    output = String.format("The high price for a round trip from %s to %s is %d",origin,dest,high_round);
                    System.out.println(output);
                    output = String.format("The low price for a round trip from %s to %s is %d",origin,dest,low_round);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(dbinterface.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
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
                try {
                    System.out.println("Find Reservation Info:");
                    System.out.print("Please enter reservation number: ");
                    String resnum = scan.next();
                    String resquery = "SELECT * FROM Reservation_details WHERE reservation_number = ?";
                    PreparedStatement findlegs = connection.prepareStatement(resquery);
                    findlegs.setString(1, resnum);
                    ResultSet rs = findlegs.executeQuery();
                    if(!rs.next()){
                        System.out.println("Sorry, that wasn't a valid reservation number.");
                    }
                    else{
                        while(rs.next()){
                            System.out.println(rs);
                        
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(dbinterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(in == '0'){
                try {
                    System.out.println("Ticket Reservation:");
                    System.out.print("Please enter reservation number: ");
                    String resnum = scan.next();
                    String resquery = "UPDATE Reservation SET tickted = 1 WHERE reservation_number = ?";
                    PreparedStatement updateRes = connection.prepareStatement(resquery);
                    updateRes.setString(1,resnum);
                    updateRes.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(dbinterface.class.getName()).log(Level.SEVERE, null, ex);
                }
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

