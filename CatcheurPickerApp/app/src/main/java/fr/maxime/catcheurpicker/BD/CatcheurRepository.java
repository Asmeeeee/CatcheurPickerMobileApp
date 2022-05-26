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
import fr.maxime.catcheurpicker.Model.CatcheurWithTeams;

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

    public List<CatcheurWithTeams> getCatcheursWithTeams(){return catcheurDao.getCatcheursWithTeams();}

    public Catcheur getCatcheurById(String id){ return  catcheurDao.getCatcheurById(id);}

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
        System.out.println("C est l'insertion du" + catcheur);
        new insertAsyncTask(catcheurDao).execute(catcheur);
    }

    public static class insertAsyncTask extends  AsyncTask<Catcheur, Void, Void>{
        private CatcheurDao catcheurDao;
        insertAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

        @Override
        protected Void doInBackground(Catcheur... catcheurs) {
            System.out.println("insertion");
            catcheurDao.insert(catcheurs[0]);
            return null;
        }
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
