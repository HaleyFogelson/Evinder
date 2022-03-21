package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnection{
    public static String url;
    public static Connection connection;

    static{
        url = "jdbc:sqlite:/tmp/test.db";
        connection = null;
    }

    public static Connection getConnection(){
        if(connection == null){
            try{
                Class.forName("org.sqlite.JDBC");
                connection  =  DriverManager.getConnection(url);
            } catch(Exception e){
                System.out.println("Connection failed : " + e);
                return null;
            }
            System.out.println("Seems like the connection is established.");
        }
        return connection;
    }

    public static void deconnection(){
        try{
            connection.close();
            System.out.println("Disconnection succesful.");
        } catch(Exception e){
            System.out.println("Deconnection failed : " + e);
        }
    }
}