package fr.maxime.catcheurpicker.BD;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;

@Dao
public interface TeamDao{
    @Transaction
    @Delete
    void delete(Team team);

    @Transaction
    @Insert(onConflict = REPLACE)
    void insert(Team team);

    @Transaction
    @Update
    void update(Team team);

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
    Team getTeamById(Integer id);

    @Transaction
    @Query("SELECT * FROM teamTable")
    List<TeamWithCatcheurs> getTeamsWithCatcheurs();

    @Transaction
    @Query("SELECT * FROM teamTable where teamId = :idTeam")
    TeamWithCatcheurs getTeamsWithCatcheursByTeam(int idTeam);

    @Transaction
    @Insert(onConflict = REPLACE)
    void insertTeamWithCatcheurs(TeamCatcheurCrossRef teamCatcheurCrossRef);

    @Transaction
    @Delete()
    void deleteTeamWithCatcheurs(TeamCatcheurCrossRef teamCatcheurCrossRef);

    @Transaction
    @Query("SELECT max(teamId) FROM teamTable")
    Integer getTeamIdMax();

    @Transaction
    @Query("SELECT * FROM teamTable where nomTeam LIKE '%' || :value || '%'")
    List<Team> searchTeam(String value);
}
