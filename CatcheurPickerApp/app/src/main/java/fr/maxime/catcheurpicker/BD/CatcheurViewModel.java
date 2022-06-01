package fr.maxime.catcheurpicker.BD;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.CatcheurWithTeams;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;

public class CatcheurViewModel extends AndroidViewModel {
    private CatcheurRepository catcheurRepository;
    private LiveData<Integer> nbCatcheursLD;
    private LiveData<List<Catcheur>> allCatcheursLD;

    public CatcheurViewModel(Application application){
        super(application);
        catcheurRepository = new CatcheurRepository(application);
        nbCatcheursLD = catcheurRepository.getNbCatcheurLD();
        allCatcheursLD = catcheurRepository.getAllCatcheursLD();
    }
    public List<Catcheur> searchCatcheur(String value) throws ExecutionException, InterruptedException { return catcheurRepository.searchCatcheur(value);}

    public List<CatcheurWithTeams> getCatcheursWithTeams() throws ExecutionException, InterruptedException{ return catcheurRepository.getCatcheursWithTeams();}

    public CatcheurWithTeams getCatcheurWithTeamsById(int id) throws ExecutionException, InterruptedException{ return catcheurRepository.getCatcheurWithTeamsById(id);}

    public Catcheur getCatcheurById(Integer id) throws ExecutionException, InterruptedException{ return catcheurRepository.getCatcheurById(id);}

    public Integer getCatcheurIdMax() throws ExecutionException, InterruptedException{ return catcheurRepository.getCatcheurIdMax();}

    public LiveData<Integer> getNbCatcheursLD() {
        return nbCatcheursLD;
    }

    public LiveData<List<Catcheur>> getAllCatcheursLD() {
        return allCatcheursLD;
    }

    public void delete(){
        catcheurRepository.deleteAll();
    }

    public void deleteOneCatcheur(Catcheur catcheur){
        catcheurRepository.delete(catcheur);
    }

    public void insert(Catcheur catcheur){
        catcheurRepository.insert(catcheur);
    }

    public void update(Catcheur catcheur){catcheurRepository.update(catcheur);}

    public void insertTeamWithCatcheursAsyncTask(Catcheur catcheur, Team team){ catcheurRepository.insertTeamWithCatcheurs(catcheur, team);}
}
