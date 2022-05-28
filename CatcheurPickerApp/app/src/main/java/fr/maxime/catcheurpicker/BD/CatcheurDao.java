package fr.maxime.catcheurpicker.BD;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.CatcheurWithTeams;
<<<<<<< HEAD
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;
=======
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;
>>>>>>> jeremy

@Dao
public interface CatcheurDao {
    @Transaction
    @Delete
    void delete(Catcheur catcheur);

    @Transaction
<<<<<<< HEAD
    @Insert
=======
    @Insert(onConflict = REPLACE)
>>>>>>> jeremy
    void insert(Catcheur catcheur);

    @Transaction
    @Query("DELETE from catcheurTable")
    void deleteAllCatcheur();

    @Transaction
    @Query("SELECT count(*) from catcheurTable")
    LiveData<Integer> nbCatcheurLD();

    @Transaction
    @Query("SELECT * from catcheurTable")
    LiveData<List<Catcheur>> getAllCatcheurLD();

    @Transaction
    @Query("SELECT * from catcheurTable where catcheurId = :id")
    Catcheur getCatcheurById(String id);

    @Transaction
    @Query("SELECT * FROM catcheurTable")
    List<CatcheurWithTeams> getCatcheursWithTeams();

    @Query("SELECT * FROM catcheurTable")
    List<Catcheur> getAllCatcheurs();

<<<<<<< HEAD
=======
    @Transaction
    @Insert(onConflict = REPLACE)
    void insertTeamWithCatcheurs(TeamCatcheurCrossRef teamCatcheurCrossRef);

>>>>>>> jeremy
}
