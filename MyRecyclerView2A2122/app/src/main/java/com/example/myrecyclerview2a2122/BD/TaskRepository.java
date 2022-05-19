package com.example.myrecyclerview2a2122.BD;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.myrecyclerview2a2122.Model.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<Integer> nbTasksLD;
    private LiveData<List<Task>> allTasksLD;

    public TaskRepository(Application application){
        TaskRoomDatabase taskRoomDatabase = TaskRoomDatabase.getDatabase(application);
        taskDao = taskRoomDatabase.taskDao();
        nbTasksLD = taskDao.nbTasksLD();
        allTasksLD = taskDao.getAllTasksLD();
    }

    public LiveData<Integer> getNbTasksLD() {
        return nbTasksLD;
    }

    public LiveData<List<Task>> getAllTasksLD() {
        return allTasksLD;
    }

    public void deleteAll(){
        new deleteAsyncTask(taskDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Void,Void,Void>{
        private TaskDao taskDao;

        deleteAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTask();
            return null;
        }
    }

    public Integer getNbTasks() {
        try {
            return new getNbTaskAsyncTask(taskDao).execute().get();
        }catch (Exception e){
            Log.d("MesLogs","pb getNbTasks");
        }
        return null;

    }

    private static class getNbTaskAsyncTask extends AsyncTask<Void,Void,Integer>{
        private TaskDao taskDao;

        getNbTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return taskDao.nbTasks();
        }
    }

    public void insert(Task task){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insert(task);
            }
        });
    }

    public void delete(Task task){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.delete(task);
            }
        });
    }
}
