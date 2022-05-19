package fr.maxime.catcheurpicker.BD;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.maxime.catcheurpicker.Model.Catcheur;

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
}
