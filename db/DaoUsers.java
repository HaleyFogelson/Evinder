package db;

import bean.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DaoUsers {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static{
        connection = DBConnection.getConnection();
        preparedStatement = null;
        resultSet = null;
    }

    public static User getUser(int user_id){
        try{
            String query = "SELECT * FROM USERS WHERE user_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setUser_id(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setWhatsapp(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                user.setAbout_me(resultSet.getString(6));
                user.setPassword(resultSet.getString(7));
                return user;
            } else {
                return null;
            }
        } catch(Exception e){
            System.out.println("Fail of user recovery with user_id : " + user_id + " : " + e);
            return null;
        }
    }

    public static User getUser(String name){
        try{
            String query = "SELECT * FROM USERS WHERE name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setUser_id(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setEmail(resultSet.getString(3));
                user.setWhatsapp(resultSet.getString(4));
                user.setAge(resultSet.getInt(5));
                user.setAbout_me(resultSet.getString(6));
                user.setPassword(resultSet.getString(7));
                return user;
            } else {
                return null;
            }
        } catch(Exception e){
            System.out.println("Fail of user recovery with name : " + name + " : " + e);
            return null;
        }
    }

    public static ArrayList<User> getUsers(){
        ArrayList<User> usersList = new ArrayList<User>();
        try{
            String query = "SELECT * FROM USERS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                usersList.add(DaoUser.getUser(resultSet.getInt(1)));
            }
            return usersList;
        } catch(Exception e){
            System.out.println("Problem while users recovery : " + e);
            return usersList;
        }
    }

    public static int addUser(User user){
        try{
            String query = "INSERT INTO USERS(name, whatsapp, email, age, about_me, password) VALUES(?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getWhatsapp());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setInt(4,user.getAge());
            preparedStatement.setString(5,user.getAbout_me());
            preparedStatement.setString(6,user.getPassword());
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while adding user : " + e);
            return -1;
        }
    }

    public static int deleteUser(int user_id){
        try{
            String query = "DELETE FROM USERS WHERE user_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while deleting user with user_id : " + user_id + " : " + e);
            return -1;
        }
    }

    public static Boolean existsUser(int user_id){
        try{
            String query = "SELECT * FROM USERS WHERE user_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            System.out.println("Error while checking the existence of the user with user_id : " + user_id + " : " + e);
            return null;
        }
    }

    public static Boolean existsUser(String name){
        try{
            String query = "SELECT * FROM USERS WHERE name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            System.out.println("Error while checking the existence of the user with name : " + name + " : " + e);
            return null;
        }
    }
}