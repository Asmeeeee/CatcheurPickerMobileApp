package com.example.myrecyclerview2a2122.BD;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myrecyclerview2a2122.Model.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Delete
    void delete(Task task);

    @Insert
    void insert(Task task);

    @Query("DELETE from taskTable")
    void deleteAllTask();

    @Query("SELECT count(*) from taskTable")
    Integer nbTasks();

    @Query("SELECT count(*) from taskTable")
    LiveData<Integer> nbTasksLD();

    @Query("SELECT * from taskTable")
    LiveData<List<Task>> getAllTasksLD();
}
