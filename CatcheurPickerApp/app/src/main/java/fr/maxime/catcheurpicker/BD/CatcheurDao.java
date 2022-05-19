package fr.maxime.catcheurpicker.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Catcheur;

@Dao
public interface CatcheurDao {
    @Delete
    void delete(Catcheur catcheur);

    @Insert
    void insert(Catcheur catcheur);

    @Query("DELETE from catcheurTable")
    void deleteAllCatcheur();

    @Query("SELECT count(*) from catcheurTable")
    LiveData<Integer> nbCatcheurLD();

    @Query("SELECT * from catcheurTable")
    LiveData<List<Catcheur>> getAllCatcheurLD();
}
