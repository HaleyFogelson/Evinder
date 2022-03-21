package db;

import bean.Association;
import bean.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DaoAssociations {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static{
        connection = DBconnection.getconnection();
        preparedStatement = null;
        resultSet = null;
    }

    public static Association getAssociationByIds(int user_id_assoc, int event_id_assoc){
        try{
            String query = "SELECT * FROM ASSOCIATIONS WHERE user_id_assoc=? AND event_id_assoc=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id_assoc);
            preparedStatement.setInt(2,event_id_assoc);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Association association = new Association();
                association.setUser_id_assoc(resultSet.getInt(1));
                association.setEvent_id_assoc(resultSet.getInt(2));
                return association;
            } else{
                return null;
            }
        } catch(Exception e){
            System.out.println("Fail of association recovery with user_id_assoc : " + user_id_assoc + " and event_id_assoc : " + event_id_assoc + " : " + e);
            return null;
        }
    }

    public static ArrayList<Association> getAssociations(){
        ArrayList<Association> associationsList = new ArrayList<Association>();
        try{
            String query = "SELECT * FROM ASSOCIATIONS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                associationsList.add(DaoAssociations.getAssociationByIds(resultSet.getInt(1)),resultSet.getInt(2));
            }
            return associationsList;
        } catch(Exception e){
            System.out.println("Problem while assocaitions recovery : " + e);
            return associationsList;
        }
    }

    public static int addAssociation(Association association){
        try{
            String query = "INSERT INTO ASSOCIATIONS(user_id_assoc, event_id_assoc) VALUES(?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,association.getUser_id_assoc());
            preparedStatement.setInt(2,association.getEvent_id_assoc());
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while adding association : " + e);
            return -1;
        }
    }

    public static int deleteAssociation(int user_id_assoc, int event_id_assoc){
        try{
            String query = "DELETE FROM ASSOCIATIONS WHERE user_id_assoc=? AND event_id_assoc=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id_assoc);
            preparedStatement.setInt(2,event_id_assoc);
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while deleting association with user_id_assoc : " + user_id_assoc + " and event_id : " + event_id_assoc + " : " + e);
            return -1;
        }
    }

    public static Boolean existsAssociation(int user_id_assoc, int event_id_assoc){
        try{
            String query = "SELECT * FROM ASSOCIATIONS WHERE user_id_assoc=? AND event_id_assoc=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,user_id_assoc);
            preparedStatement.setInt(2,event_id_assoc);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            System.out.println("Error while checking the existence of the association with user_id_assoc : " + user_id_assoc + " and event_id_assoc : " + event_id_assoc + " : " + e);
            return null;
        }
    }
}
