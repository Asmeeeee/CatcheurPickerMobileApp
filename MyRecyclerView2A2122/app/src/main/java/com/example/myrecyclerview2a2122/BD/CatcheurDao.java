package com.example.myrecyclerview2a2122.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecyclerview2a2122.Model.Catcheur;

import java.util.List;

@Dao
public interface CatcheurDao {
    @Delete
    void delete(Catcheur catcheur);

    @Insert
    void insert(Catcheur task);

    @Query("DELETE from catcheurtable")
    void deleteAllTask();

    @Query("SELECT count(*) from catcheurTable")
    Integer nbTasks();

    @Query("SELECT count(*) from catcheurTable")
    LiveData<Integer> nbTasksLD();

    @Query("SELECT * from catcheurTable")
    LiveData<List<Catcheur>> getAllTasksLD();
}
