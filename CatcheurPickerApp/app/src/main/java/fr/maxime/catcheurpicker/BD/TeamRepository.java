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
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;

public class TeamRepository {
    private TeamDao teamDao;
    private LiveData<Integer> nbTeamLD;
    private LiveData<List<Team>> allTeamsLD;

    public TeamRepository(Application application){
        RoomDatabase roomDatabase = RoomDatabase.getDatabase(application);
        teamDao = roomDatabase.teamDao();
        nbTeamLD = teamDao.nbTeamLD();
        allTeamsLD = teamDao.getAllTeamLD();

    }

    public List<Team> searchTeam(String value) throws ExecutionException, InterruptedException {
        return new searchTeamAsyncTask(teamDao).execute(value).get();
    }

    public static class searchTeamAsyncTask extends AsyncTask<String, Void, List<Team>>{
        private TeamDao teamDao;
        searchTeamAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected List<Team> doInBackground(String... strings) {
            return teamDao.searchTeam(strings[0]);
        }
    }

    public TeamWithCatcheurs getTeamsWithCatcheursByTeam(int id) throws ExecutionException, InterruptedException{
        return new getTeamsWithCatcheursByTeamAsyncTask(teamDao).execute(id).get();
    }

    public static class getTeamsWithCatcheursByTeamAsyncTask extends AsyncTask<Integer, Void, TeamWithCatcheurs>{
        private TeamDao teamDao;
        getTeamsWithCatcheursByTeamAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected TeamWithCatcheurs doInBackground(Integer... integers) {
            return teamDao.getTeamsWithCatcheursByTeam(integers[0]);
        }
    }

    public List<TeamWithCatcheurs> getTeamsWithCatcheurs() throws ExecutionException, InterruptedException {
        return new getTeamsWithCatcheursAsyncTask(teamDao).execute().get();
    }

    public static class getTeamsWithCatcheursAsyncTask extends  AsyncTask<Void, Void, List<TeamWithCatcheurs>>{
        private TeamDao teamDao;
        getTeamsWithCatcheursAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected List<TeamWithCatcheurs> doInBackground(Void... voids) {
            return teamDao.getTeamsWithCatcheurs();
        }
    }

    public Integer getTeamIdMax() throws ExecutionException, InterruptedException{
        return new getTeamIdMaxAsyncTask(teamDao).execute().get();
    }

    public static class getTeamIdMaxAsyncTask extends AsyncTask<Void, Void, Integer>{
        private TeamDao teamDao;
        getTeamIdMaxAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected Integer doInBackground(Void... voids) {
            return teamDao.getTeamIdMax();
        }
    }

    public Team getTeamById(int id) throws ExecutionException, InterruptedException{
        return new getTeamByIdAsyncTask(teamDao).execute(id).get();
    }

    public static class getTeamByIdAsyncTask extends  AsyncTask<Integer, Void, Team>{
        private TeamDao teamDao;
        getTeamByIdAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected Team doInBackground(Integer... integers) {
            return teamDao.getTeamById(integers[0]);
        }
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
        new insertTeamWithCatcheursAsyncTask(teamDao).execute(new AsyncTaskTwoParams(catcheur, team));
    }

    public static  class insertTeamWithCatcheursAsyncTask extends  AsyncTask<AsyncTaskTwoParams,Void,Void>{

        private  TeamDao teamDao;

        public insertTeamWithCatcheursAsyncTask(TeamDao teamDao){
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(AsyncTaskTwoParams... asyncTaskTwoParams) {
            teamDao.insertTeamWithCatcheurs(new TeamCatcheurCrossRef(asyncTaskTwoParams[0].team.getTeamId(), asyncTaskTwoParams[0].catcheur.getCatcheurId()));
            return null;
        }
    }

    public void deleteTeamWithCatcheurs(Catcheur catcheur, Team team){
        new deleteTeamWithCatcheursAsyncTask(teamDao).execute(new AsyncTaskTwoParams(catcheur, team));
    }

    public static class deleteTeamWithCatcheursAsyncTask extends AsyncTask<AsyncTaskTwoParams, Void, Void>{
        private TeamDao teamDao;
        deleteTeamWithCatcheursAsyncTask(TeamDao teamDao){ this.teamDao = teamDao;}


        @Override
        protected Void doInBackground(AsyncTaskTwoParams... asyncTaskTwoParams) {
            teamDao.deleteTeamWithCatcheurs(new TeamCatcheurCrossRef(asyncTaskTwoParams[0].team.getTeamId(), asyncTaskTwoParams[0].catcheur.getCatcheurId()));
            return null;
        }
    }

    public LiveData<Integer> getNbTeamLD(){return  this.nbTeamLD;}

    public LiveData<List<Team>> getAllTeamsLD(){return this.allTeamsLD;}

    public void deleteAll(){ new deleteAllAsyncTask(teamDao).execute();}

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private TeamDao teamDao;

        public deleteAllAsyncTask(TeamDao teamDao){ this.teamDao = teamDao;}

        @Override
        protected Void doInBackground(Void... voids) {
            teamDao.deleteAllTeam();
            return null;
        }
    }

    public void insert(Team team){
        new insertAsyncTask(teamDao).execute(team);
    }

    public static class insertAsyncTask extends  AsyncTask<Team, Void, Void>{
        private TeamDao teamDao;
        insertAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.insert(teams[0]);
            return null;
        }
    }

    public void update(Team team){
        new updateAsyncTask(teamDao).execute(team);
    }

    public static class updateAsyncTask extends AsyncTask<Team, Void, Void>{
        private TeamDao teamDao;
        updateAsyncTask(TeamDao teamDao){ this.teamDao= teamDao;}

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.update(teams[0]);
            return null;
        }
    }

    public void delete(Team team){
        new deleteAsyncTask(teamDao).execute(team);
    }

    public static class deleteAsyncTask extends  AsyncTask<Team, Void, Void>{
        private TeamDao teamDao;
        deleteAsyncTask(TeamDao teamDao){this.teamDao = teamDao;}

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.delete(teams[0]);
            return null;
        }
    }

}
