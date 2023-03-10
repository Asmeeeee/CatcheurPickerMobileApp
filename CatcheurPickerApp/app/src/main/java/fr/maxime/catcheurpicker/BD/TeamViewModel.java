package fr.maxime.catcheurpicker.BD;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;

public class TeamViewModel extends AndroidViewModel {
    private TeamRepository teamRepository;
    private LiveData<Integer> nbTeamsLD;
    private LiveData<List<Team>> allTeamsLD;

    public TeamViewModel(Application application){
        super(application);
        teamRepository = new TeamRepository(application);
        nbTeamsLD = teamRepository.getNbTeamLD();
        allTeamsLD = teamRepository.getAllTeamsLD();
    }

    public List<Team> searchTeam(String value) throws ExecutionException, InterruptedException { return teamRepository.searchTeam(value);}

    public List<TeamWithCatcheurs> getTeamsWithCatcheurs() throws ExecutionException, InterruptedException {return teamRepository.getTeamsWithCatcheurs();}

    public TeamWithCatcheurs getTeamWithCatcheursById(int id) throws ExecutionException, InterruptedException{ return teamRepository.getTeamsWithCatcheursByTeam(id);}

    public Team getTeamById(int id) throws ExecutionException, InterruptedException{ return teamRepository.getTeamById(id);}

    public Integer getTeamIdMax() throws ExecutionException, InterruptedException{ return teamRepository.getTeamIdMax();}

    public LiveData<Integer> getNbTeamsLD() {
        return nbTeamsLD;
    }

    public LiveData<List<Team>> getAllTeamsLD() {
        return allTeamsLD;
    }

    public void delete(){
        teamRepository.deleteAll();
    }

    public void deleteOneTeam(Team team){
        teamRepository.delete(team);
    }

    public void insert(Team team){
        System.out.println("Je passe dans ViewModel");
        teamRepository.insert(team);
    }

    public void update(Team team){
        teamRepository.update(team);
    }

    public void insertTeamWithCatcheursAsyncTask(Catcheur catcheur, Team team){ teamRepository.insertTeamWithCatcheurs(catcheur, team);}

    public void deleteTeamWithCatcheursAsyncTask(Catcheur catcheur, Team team){ teamRepository.deleteTeamWithCatcheurs(catcheur, team);}
}
