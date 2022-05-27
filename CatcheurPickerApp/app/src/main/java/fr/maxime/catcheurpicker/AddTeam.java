package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class AddTeam extends AppCompatActivity {

    private List<Team> dataTeam = new ArrayList<>();
    private List<Catcheur> catcheursSelected = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterTeam customAdapterTeam;
    private TeamViewModel teamViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_team);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterTeam = new CustomAdapterTeam(dataTeam);


        try {
            Bundle bundle = this.getIntent().getExtras();
            catcheursSelected = (List<Catcheur>) bundle.get("catcheursSelected");
            System.out.println("Resultat");
            System.out.println(catcheursSelected);
        }
        catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
        CustomAdapterTeam.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick ShowTeams");
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

    public void addTeam(View view){
        System.out.println("Je passe dans addTeam");
        EditText fieldNomTeam = findViewById(R.id.fieldNomTeam);
        String strNomTeam = fieldNomTeam.getText().toString();
        teamViewModel.insert(new Team(strNomTeam, "image"));
    }

    //-------------------------------------------------------------------------------------
    public void goToAddCatcheur(View view){
        Intent intent = new Intent(this, AddCatcheur.class);
        startActivity(intent);
        finish();
    }


    public void goToShowCatcheurs(View view){
        Intent intent = new Intent(this, ShowCatcheurs.class);
        startActivity(intent);
        finish();
    }

    public void goToLinkCatcheursToTeam(View view) {
        Intent intent = new Intent(this, LinkCatcheursToTeam.class);
        startActivity(intent);
        finish();
    }
}