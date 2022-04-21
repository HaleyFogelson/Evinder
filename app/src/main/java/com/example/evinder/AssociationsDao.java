package com.example.evinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface AssociationsDao {
    /**
    @Query("SELECT * FROM Associations")
    List<Associations> getAllAssociations();

    @Query("SELECT * FROM Associations WHERE user_id_assoc=:user_id_assoc AND event_id_assoc=:event_id_assoc")
    Associations getAssociationByIds(int user_id_assoc, int event_id_assoc);

    @Query("SELECT * FROM Associations WHERE user_id_assoc=:user_id_assoc")
    List<Associations> getAssociationsForUser(int user_id_assoc);


    @Query("SELECT * FROM Associations WHERE event_id_assoc=:event_id_assoc")
    List<Users> getUsersForAssociation(int event_id_assoc);
     */
    
    @Insert
    long insert(Associations association);

    @Update
    public void update(Associations association);

    @Delete
    void delete(Associations association);
}