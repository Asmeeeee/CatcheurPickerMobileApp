package fr.maxime.catcheurpicker.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;

@Dao
public interface TeamDao{
    @Transaction
    @Delete
    void delete(Team team);

    @Transaction
    @Insert
    void insert(Team catcheur);

    @Transaction
    @Query("DELETE from teamTable")
    void deleteAllTeam();

    @Transaction
    @Query("SELECT count(*) from teamTable")
    LiveData<Integer> nbTeamLD();

    @Transaction
    @Query("SELECT * from teamTable")
    LiveData<List<Team>> getAllTeamLD();

    @Transaction
    @Query("SELECT * from teamTable where teamId = :id")
    Team getTeamById(String id);

    @Transaction
    @Query("SELECT * FROM teamTable")
    List<TeamWithCatcheurs> getTeamsWithCatcheurs();
}
