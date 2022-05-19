package fr.maxime.catcheurpicker.BD;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.maxime.catcheurpicker.Model.Catcheur;

public class CatcheurRepository {
    private CatcheurDao catcheurDao;
    private LiveData<Integer> nbCatcheurLD;
    private LiveData<List<Catcheur>> allCatcheursLD;

    public CatcheurRepository(Application application){
        RoomDatabase roomDatabase = RoomDatabase.getDatabase(application);
        catcheurDao = roomDatabase.catcheurDao();
        nbCatcheurLD = catcheurDao.nbCatcheurLD();
        allCatcheursLD = catcheurDao.getAllCatcheurLD();
    }

    public LiveData<Integer> getNbCatcheurLD() {
        return nbCatcheurLD;
    }

    public LiveData<List<Catcheur>> getAllCatcheursLD() {
        return allCatcheursLD;
    }

    public void deleteAll(){
        new deleteAsyncTask(catcheurDao).execute();
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void>{
        private CatcheurDao catcheurDao;

        deleteAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

        @Override
        protected  Void doInBackground(Void... voids){
            catcheurDao.deleteAllCatcheur();
            return null;
        }
    }

    public void insert(Catcheur catcheur){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                catcheurDao.insert(catcheur);
            }
        });
    }

    public void delete(Catcheur catcheur){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                catcheurDao.delete(catcheur);
            }
        });
    }


}
