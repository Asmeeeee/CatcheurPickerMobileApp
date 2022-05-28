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
import java.util.concurrent.ExecutionException;


import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class ShowTeams extends AppCompatActivity {
    private List<Team> dataTeam = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterTeam customAdapterTeam;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_teams);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        RecyclerView recyclerViewTeams = findViewById(R.id.recyclerViewTeams);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterTeam = new CustomAdapterTeam(dataTeam);
        CustomAdapterTeam.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v){
                Log.d("MesLogs","onItemClick MainActivity");
                Team team = dataTeam.get(position);
                TeamWithCatcheurs teamWithCatcheurs = teamViewModel.getTeamWithCatcheursById(team.getTeamId());
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
        recyclerViewTeams.setAdapter(customAdapterTeam);
        recyclerViewTeams.setLayoutManager(linearLayoutManager);


        teamViewModel.getNbTeamsLD().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.textViewNbTeamLD);
                textView.setText("nb Team LD: "+ integer);
            }
        });

        teamViewModel.getAllTeamsLD().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                dataTeam = teams;
                customAdapterTeam.setData(teams);
                customAdapterTeam.notifyDataSetChanged();
            }
        });

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

    public void goToShowCatcheurs(View view){
        Intent intent = new Intent(this, ShowCatcheurs.class);
        startActivity(intent);
        finish();
    }
}