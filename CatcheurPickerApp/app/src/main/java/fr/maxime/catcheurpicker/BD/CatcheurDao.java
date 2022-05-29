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
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;

@Dao
public interface CatcheurDao {
    @Transaction
    @Delete
    void delete(Catcheur catcheur);

    @Transaction
    @Insert(onConflict = REPLACE)
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
    Catcheur getCatcheurById(Integer id);

    @Transaction
    @Query("SELECT * FROM catcheurTable")
    List<CatcheurWithTeams> getCatcheursWithTeams();

    @Transaction
    @Query("SELECT * FROM catcheurTable where catcheurId = :idCatcheur")
    CatcheurWithTeams getCatcheurWithTeamsById(int idCatcheur);

    @Query("SELECT * FROM catcheurTable")
    List<Catcheur> getAllCatcheurs();

    @Transaction
    @Insert(onConflict = REPLACE)
    void insertTeamWithCatcheurs(TeamCatcheurCrossRef teamCatcheurCrossRef);

    @Transaction
    @Query("SELECT max(catcheurId) FROM catcheurTable")
    Integer getCatcheurIdMax();

}
