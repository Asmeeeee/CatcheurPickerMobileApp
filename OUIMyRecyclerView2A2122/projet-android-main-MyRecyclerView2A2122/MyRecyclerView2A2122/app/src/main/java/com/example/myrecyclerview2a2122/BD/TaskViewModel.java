package com.example.myrecyclerview2a2122.BD;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myrecyclerview2a2122.Model.Task;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private LiveData<Integer> nbTaskLD;
    private LiveData<List<Task>> allTaskLD;


   public TaskViewModel(Application application){
        super(application);
        taskRepository = new TaskRepository(application);
        nbTaskLD = taskRepository.getNbTasksLD();
        allTaskLD = taskRepository.getAllTasksLD();
    }

    public LiveData<Integer> getNbTaskLD() {
        return nbTaskLD;
    }

    public LiveData<List<Task>> getAllTaskLD() {
        return allTaskLD;
    }

    public void delete(){
        taskRepository.deleteAll();
    }

    public void deleteOneTask(Task task){ taskRepository.delete(task);}

    public void insert(Task task){
        taskRepository.insert(task);
    }

    public Integer nbTask(){
        return taskRepository.getNbTasks();
    }
}
