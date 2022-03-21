package db;

import bean.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class DaoEvents {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static{
        connection = DBconnection.getconnection();
        preparedStatement = null;
        resultSet = null;
    }

    public static Event getEventById(int event_id){
        try{
            String query = "SELECT * FROM EVENTS WHERE event_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,event_id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Event event = new Event();
                event.setEvent_id(resultSet.getInt(1));
                event.setName(resultSet.getString(2));
                event.setDescription(resultSet.getString(3));
                event.setDate(resultSet.getString(4));
                event.setCreator(resultSet.getInt(5));
                return event;
            } else{
                return null;
            }
        } catch(Exception e){
            System.out.println("Fail of event recovery with event_id : " + event_id + " : " + e);
            return null;
        }
    }

    public static ArrayList<Event> getEventsByName(String name){
        ArrayList<Event> eventsList = new ArrayList<Event>();
        try{
            String query = "SELECT * FROM EVENTS WHERE name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                eventsList.add(DaoEvents.getEventById(resultSet.getInt(1)));
            }
            if(eventsList.isEmpty()){
                return null;
            } else {
                return eventsList;
            }
        } catch(Exception e){
            System.out.println("Fail of event recovery with name : " + name + " : " + e);
            return null;
        }
    }

    public static ArrayList<Event> getEvents(){
        ArrayList<Event> eventsList = new ArrayList<Event>();
        try{
            String query = "SELECT * FROM EVENTS";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                eventsList.add(DaoEvents.getEventById(resultSet.getInt(1)));
            }
            return eventsList;
        } catch(Exception e){
            System.out.println("Problem while events recovery : " + e);
            return eventsList;
        }
    }

    public static int addEvent(Event event){
        try{
            String query = "INSERT INTO EVENTS(name, description, date, creator) VALUES(?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,event.getName());
            preparedStatement.setString(2,event.getDescription());
            preparedStatement.setString(3,event.getDate().toString());
            preparedStatement.setInt(4,event.getCreator());
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while adding event : " + e);
            return -1;
        }
    }

    public static int deleteEventById(int event_id){
        try{
            String query = "DELETE FROM EVENTS WHERE event_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,event_id);
            return preparedStatement.executeUpdate();
        } catch(Exception e){
            System.out.println("Fail while deleting event with event_id : " + event_id + " : " + e);
            return -1;
        }
    }

    public static Boolean existsEventById(int event_id){
        try{
            String query = "SELECT * FROM EVENTS WHERE event_id=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,event_id);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            System.out.println("Error while checking the existence of the event with event_id : " + event_id + " : " + e);
            return null;
        }
    }

    public static Boolean existsEventByNam(String name){
        try{
            String query = "SELECT * FROM EVENTS WHERE name=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch(Exception e){
            System.out.println("Error while checking the existence of the event with name : " + name + " : " + e);
            return null;
        }
    }
}
