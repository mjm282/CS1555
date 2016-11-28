import java.sql.*;
import java.io.*;
public class dbinterface{

    private static Connection connection;
    private static String uname, passwrd;


    public static void main(String[] args){
        System.out.println("hello world");


        // change this if you're debuging 
        uname = "cas275";
        passwrd = "pitt1787";

        try{
            DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
            connection = DriverManager.getConnection(url, uname, passwrd);
        }
        catch(Exception e){
            System.out.println("error connecting + " + e);
            e.printStackTrace();
        }

        System.out.println("are you an admin? y/n");
        char in = (char) System.in.read();

        if(in == 'y'){
            adminInterface();
        }
        else{
            userInterface();
        }
    }


    public static void adminInterface(){
        System.out.println("~Admin menu~");

    }
    public static void userInterface(){
        System.out.println("User menu");
    }

}
