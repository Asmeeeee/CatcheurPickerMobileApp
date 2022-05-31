package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheursSelected;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class AddTeam extends AppCompatActivity {

    private List<Team> dataTeam = new ArrayList<>();
    private List<Catcheur> catcheursSelected = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheursSelected customAdapterCatcheursSelected;
    private TeamViewModel teamViewModel;
    private CatcheurViewModel catcheurViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_team);
        RecyclerView recyclerViewAddTeamCatcheursSelected = findViewById(R.id.recyclerViewAddTeamCatcheursSelected);
        teamViewModel = new ViewModelProvider(this).get(TeamViewModel.class);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheursSelected = new CustomAdapterCatcheursSelected(catcheursSelected);

        try {
            Bundle bundle = this.getIntent().getExtras();
            catcheursSelected = (List<Catcheur>) bundle.get("catcheursSelected");
            customAdapterCatcheursSelected.setData(catcheursSelected);
            customAdapterCatcheursSelected.notifyDataSetChanged();
        }
        catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
        CustomAdapterCatcheur.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Catcheur catcheur = catcheursSelected.get(position);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(catcheur.getNomScene())
                        .setMessage("Poids: "+catcheur.getTaille() + " Taille : "+catcheur.getTaille())
                        .show();
            }
            @Override
            public void onItemLongClick(int position, View view) {
                catcheurViewModel.deleteOneCatcheur(catcheursSelected.get(position));
            }
        });
        recyclerViewAddTeamCatcheursSelected.setAdapter(customAdapterCatcheursSelected);
        recyclerViewAddTeamCatcheursSelected.setLayoutManager(linearLayoutManager);
    }

    public void addTeam(View view)  throws ExecutionException, InterruptedException{
        EditText fieldNomTeam = findViewById(R.id.fieldNomTeam);
        String strNomTeam = fieldNomTeam.getText().toString();
        Team team = new Team(strNomTeam, "image");
        teamViewModel.insert(team);
        int idTeam = teamViewModel.getTeamIdMax();
        team = teamViewModel.getTeamById(idTeam);
        for(Catcheur catcheur : catcheursSelected){
            teamViewModel.insertTeamWithCatcheursAsyncTask(catcheur, team);
        }
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