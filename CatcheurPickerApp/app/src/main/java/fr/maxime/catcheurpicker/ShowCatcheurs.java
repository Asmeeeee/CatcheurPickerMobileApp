package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class ShowCatcheurs extends AppCompatActivity {
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
        setContentView(R.layout.activity_show_catcheurs);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        RecyclerView recyclerViewCatcheurs = findViewById(R.id.recyclerViewCatcheurs);
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
        recyclerViewCatcheurs.setAdapter(customAdapterCatcheur);
        recyclerViewCatcheurs.setLayoutManager(linearLayoutManager);


        catcheurViewModel.getNbCatcheursLD().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.textViewNbLD);
                textView.setText("nb Catcheurs LD: "+ integer);
            }
        });
        /*
        catcheurViewModel.getAllCatcheursLD().observe(this, new Observer<List<Catcheur>>() {
            @Override
            public void onChanged(List<Catcheur> catcheurs) {
                dataCatcheur = catcheurs;
                customAdapterCatcheur.setData(catcheurs);
                customAdapterCatcheur.notifyDataSetChanged();
            }
        });

         */
    }

    public void goToAddCatcheur(View view){
        Intent intent = new Intent(this, AddCatcheur.class);
        startActivity(intent);
        finish();
    }

    public void goToAddTeam(View view){
        Intent intent = new Intent(this, AddTeam.class);
        startActivity(intent);
        finish();
    }

    public void goToShowTeams(View view){
        Intent intent = new Intent(this, ShowTeams.class);
        startActivity(intent);
        finish();
    }

    public void goToShowCatcheurs(View view){
        Intent intent = new Intent(this, ShowCatcheurs.class);
        startActivity(intent);
        finish();
    }

}