package fr.maxime.catcheurpicker.BD;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

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

    public List<TeamWithCatcheurs> getCatcheursWithTeam(){return teamRepository.getTeamsWithCatcheurs();}

    public Team getTeamById(String id){ return teamRepository.getTeamById(id);}

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
        teamRepository.insert(team);
    }
}
