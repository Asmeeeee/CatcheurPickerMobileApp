package fr.maxime.catcheurpicker.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Team;

@Dao
public interface TeamDao{
    @Delete
    void delete(Team team);

    @Insert
    void insert(Team catcheur);

    @Query("DELETE from teamTable")
    void deleteAllTeam();

    @Query("SELECT count(*) from teamTable")
    LiveData<Integer> nbTeamLD();

    @Query("SELECT * from teamTable")
    LiveData<List<Team>> getAllTeamLD();
}
