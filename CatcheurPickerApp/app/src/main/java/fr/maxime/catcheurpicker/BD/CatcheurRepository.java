package fr.maxime.catcheurpicker.BD;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.CatcheurWithTeams;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;

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

    public static class AsyncTaskTwoParams{
        private Catcheur catcheur;
        private Team team;

        AsyncTaskTwoParams(Catcheur catcheur, Team team){
            this.catcheur = catcheur;
            this.team = team;
        }
    }

    public void insertTeamWithCatcheurs(Catcheur catcheur, Team team){
        new insertTeamWithCatcheursAsyncTask(catcheurDao).execute(new AsyncTaskTwoParams(catcheur, team));
    }

    public static   class insertTeamWithCatcheursAsyncTask extends  AsyncTask<AsyncTaskTwoParams,Void,Void>{

        private  CatcheurDao catcheurDao;

        public insertTeamWithCatcheursAsyncTask(CatcheurDao catcheurDao){
            this.catcheurDao = catcheurDao;
        }

        @Override
        protected Void doInBackground(AsyncTaskTwoParams... asyncTaskTwoParams) {
            catcheurDao.insertTeamWithCatcheurs(new TeamCatcheurCrossRef(asyncTaskTwoParams[0].team.getTeamId(), asyncTaskTwoParams[0].catcheur.getCatcheurId()));
            return null;
        }
    }

    public List<CatcheurWithTeams> getCatcheursWithTeams() throws ExecutionException, InterruptedException {return new getCatcheursWithTeamsAsyncTask(catcheurDao).execute().get();}

    private static class getCatcheursWithTeamsAsyncTask extends AsyncTask<Void, Void, List<CatcheurWithTeams>>{
        private CatcheurDao catcheurDao;

        getCatcheursWithTeamsAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

        @Override
        protected  List<CatcheurWithTeams> doInBackground(Void... voids){
            return catcheurDao.getCatcheursWithTeams();
        }
    }

    public Catcheur getCatcheurById(String id) throws ExecutionException, InterruptedException { return new getCatcheurByIdAsyncTask(catcheurDao).execute(id).get();}

    private static class getCatcheurByIdAsyncTask extends AsyncTask<String, Void, Catcheur>{
        private CatcheurDao catcheurDao;

        getCatcheurByIdAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

        @Override
        protected  Catcheur doInBackground(String... strings){
            return catcheurDao.getCatcheurById(strings[0]);
        }
    }


    public void deleteAll(){
        new deleteAllAsyncTask(catcheurDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private CatcheurDao catcheurDao;

        deleteAllAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

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
        new deleteAsyncTask(catcheurDao).execute(catcheur);
    }

    public static class deleteAsyncTask extends  AsyncTask<Catcheur, Void, Void>{
        private CatcheurDao catcheurDao;
        deleteAsyncTask(CatcheurDao catcheurDao){this.catcheurDao = catcheurDao;}

        @Override
        protected Void doInBackground(Catcheur... catcheurs) {
            catcheurDao.delete(catcheurs[0]);
            return null;
        }
    }

}
