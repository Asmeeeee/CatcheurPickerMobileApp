package fr.maxime.catcheurpicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheursSelected;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeamSelected;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class LinkTeamsToCatcheur extends AppCompatActivity {
    private List<Team> dataTeam = new ArrayList<>();
    private List<Team> teamsSelected = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterTeamSelected customAdapterTeamSelected;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("j'arrive dans le LinkTeamsToCatcheur");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.link_teams_to_catcheur);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        RecyclerView recyclerViewLinkCatcheursToTeam = findViewById(R.id.recyclerViewLinkTeamsToCatcheur);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterTeamSelected = new CustomAdapterTeamSelected(dataTeam);
        CustomAdapterTeamSelected.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Team team = dataTeam.get(position);
                System.out.println("Click");
                teamsSelected.add(team);
            }
            @Override
            public void onItemLongClick(int position, View view) {
                teamViewModel.deleteOneTeam(dataTeam.get(position));
            }
        });
        recyclerViewLinkCatcheursToTeam.setAdapter(customAdapterTeamSelected);
        recyclerViewLinkCatcheursToTeam.setLayoutManager(linearLayoutManager);

        teamViewModel.getAllTeamsLD().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                dataTeam = teams;
                customAdapterTeamSelected.setData(teams);
                customAdapterTeamSelected.notifyDataSetChanged();
            }
        });
    }

    public void goToAddCatcheur(View view){
        Intent intent = new Intent(this, AddCatcheur.class);
        intent.putParcelableArrayListExtra("teamsSelected", (ArrayList<? extends Parcelable>) teamsSelected);
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
