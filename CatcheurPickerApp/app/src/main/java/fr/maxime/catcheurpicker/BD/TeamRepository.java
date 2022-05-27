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
import fr.maxime.catcheurpicker.Model.Team;
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

    public List<TeamWithCatcheurs> getTeamsWithCatcheurs(){ return teamDao.getTeamsWithCatcheurs();}

    public Team getTeamById(String id){return teamDao.getTeamById(id);}

    public LiveData<Integer> getNbTeamLD(){return  this.nbTeamLD;}

    public LiveData<List<Team>> getAllTeamsLD(){return this.allTeamsLD;}

    public void deleteAll(){ new TeamRepository.deleteAsyncTask(teamDao).execute();}

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void>{
        private TeamDao teamDao;

        public deleteAsyncTask(TeamDao teamDao){ this.teamDao = teamDao;}

        @Override
        protected Void doInBackground(Void... voids) {
            teamDao.deleteAllTeam();
            return null;
        }
    }

    public void insert(Team team){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                teamDao.insert(team);
            }
        });
    }

    public void delete(Team team){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                teamDao.delete(team);
            }
        });
    }

}
