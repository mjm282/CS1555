import java.sql.*;
import java.io.*;
import java.util.*;
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
					prepStatement = connection.prepareStatement(query);
					while((line = read.readLine()) != null)
					{
						lineSplit = line.split(" ");
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
				System.out.println("Do you want to: \n" +
				"L: Load pricing information\n" +
				"C: Change the price of an existing fight");
				String priceChoice = adminScan.next();
				if(priceChoice.equals("L"))
				{
					System.out.println("Please enter full path to pricing information");
					String priceFile = adminScan.next();
					query = "INSERT INTO Price VALUES (?,?,?,?,?)";
					String line;
					String[] lineSplit;
					try{
						BufferedReader read = new BufferedReader(new FileReader(priceFile));
						prepStatement = connection.prepareStatement(query);
						while((line = read.readLine()) != null)
						{
							lineSplit = line.split(" ");
							prepStatement.setString(1, lineSplit[0]);
							prepStatement.setString(2, lineSplit[1]);
							prepStatement.setString(3, lineSplit[2]);
							prepStatement.setInt(4, Integer.parseInt(lineSplit[3]));
							prepStatement.setInt(5, Integer.parseInt(lineSplit[4]));
							
							prepStatement.executeUpdate();
						}
					}catch (Exception e){
							System.out.println("Error: " + e.getMessage());
							e.printStackTrace();
					}
				}
				else if(priceChoice.equals("C"))
				{
					System.out.println("Please enter departure city, arrival city, high price, and low price separated by spaces");
					String depCity = adminScan.next();
					String arrCity = adminScan.next();
					String high = adminScan.next();
					String low = adminScan.next();
					
					query = "UPDATE Price SET high_price = ?, low_price = ? WHERE departure_city = ? AND arrival_city = ?";
					try{
						prepStatement = connection.prepareStatement(query);
						prepStatement.setInt(1, Integer.parseInt(high));
						prepStatement.setInt(2, Integer.parseInt(low));
						prepStatement.setString(3, depCity);
						prepStatement.setString(4, arrCity);
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
						e.printStackTrace();
					}
					
				}
				else
				{
					System.out.println("Invalid");
				}
				
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
					prepStatement = connection.prepareStatement(query);
					while((line = read.readLine()) != null)
					{
						lineSplit = line.split(" ");
						
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
				System.out.println("Please enter flight number and date, separated by spaces");
				String fNumber = adminScan.next();
				String fDate = adminScan.next();
				
				query = "SELECT salutation, first_name, last_name"+
						"FROM Customer"+
						"WHERE cid IN("+
							"SELECT cid FROM Reservation"+
							"WHERE reservation_number IN ("+
								"SELECT reservation_number"+
								"FROM Reservation_details"+
								"WHERE flight_number = ? AND flight_date = ?));";
					try{
						prepStatement = connection.prepareStatement(query);
						prepStatement.setString(1, fNumber);
						java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM/DD/YYYY");
						java.sql.Date date = new java.sql.Date (df.parse(fDate).getTime());
						prepStatement.setDate(2, date);
						
						resultSet = prepStatement.executeQuery();
						
						while (resultSet.next())
						{
							System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
						}
						
					}catch (Exception e){
						System.out.println("Error: " + e.getMessage());
						e.printStackTrace();
					}
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

