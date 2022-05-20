package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class MainActivity extends AppCompatActivity {
    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private List<Team> dataTeam = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheur customAdapterCatcheur;
    private CustomAdapterTeam customAdapterTeam;
    private CatcheurViewModel catcheurViewModel;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_catcheur);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        //RecyclerView recyclerView = findViewById(R.id.recyclerview); pour la liste de catcheur ou team
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheur = new CustomAdapterCatcheur(dataCatcheur);
        customAdapterTeam = new CustomAdapterTeam(dataTeam);

        CustomAdapterCatcheur.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Catcheur catcheur = dataCatcheur.get(position);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(catcheur.getNomScene())
                        .setMessage("Poids: "+catcheur.getTaille() + " Taille : "+catcheur.getTaille())
                        .show();
            }

            @Override
            public void onItemLongClick(int position, View view) {
                catcheurViewModel.deleteOneCatcheur(dataCatcheur.get(position));
            }
        });

        CustomAdapterTeam.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Team team = dataTeam.get(position);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(team.getNomTeam())
                        .setMessage("Nom de la team: "+team.getNomTeam())
                        .show();
            }

            @Override
            public void onItemLongClick(int position, View view) {
                teamViewModel.deleteOneTeam(dataTeam.get(position));
            }
        });
    }

    public void addCatcheur(View view) {
        catcheurViewModel.insert(new Catcheur(R.id.fieldNomCatcheur+"", R.id.fieldPoids, R.id.fieldTaille, "image", R.id.fieldNomCatcheur+""));
    }
}