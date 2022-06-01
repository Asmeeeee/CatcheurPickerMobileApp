package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
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
    private Team teamAModifier;
    private Team teamAModifier2;
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
            System.out.println("Les catcheurs selected apres sélection");
            System.out.println(catcheursSelected);
            customAdapterCatcheursSelected.setData(catcheursSelected);
            customAdapterCatcheursSelected.notifyDataSetChanged();
        }
        catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
        try{
            Bundle bundle2 = this.getIntent().getExtras();
            teamAModifier = (Team) bundle2.get("teamAModifier");
            System.out.println("teamAModifier");
            System.out.println(teamAModifier);
            //Merttre les valeur de team dans les champs
            EditText nomTeam = findViewById(R.id.fieldNomTeam);
            nomTeam.setText(teamAModifier.getNomTeam());
            EditText urlImage = findViewById(R.id.fieldImageTeam);
            urlImage.setText(teamAModifier.getImage());
            //mettre a jour la liste de catcheur selectionné par rapport à la team.
            if(catcheursSelected == null){
                System.out.println("Liste remis comme avant");
                catcheursSelected = teamViewModel.getTeamWithCatcheursById(teamAModifier.getTeamId()).catcheurs;
            }
            else{
                catcheursSelected = (List<Catcheur>) bundle2.get("catcheursSelected");
                System.out.println("Les catcheurs selected apres sélection");
                System.out.println(catcheursSelected);
            }
            customAdapterCatcheursSelected.setData(catcheursSelected);
            customAdapterCatcheursSelected.notifyDataSetChanged();
        }
        catch (Exception exception){
            exception.printStackTrace();
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
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
                catcheurViewModel.deleteOneCatcheur(catcheursSelected.get(position));
            }

            @Override
            public void onItemClickDelete(int position, View v) {

            }

            @Override
            public void onItemModifier(int position, View v) {

            }
        });
        recyclerViewAddTeamCatcheursSelected.setAdapter(customAdapterCatcheursSelected);
        recyclerViewAddTeamCatcheursSelected.setLayoutManager(linearLayoutManager);
    }

    public void addTeam(View view)  throws ExecutionException, InterruptedException{
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
        EditText fieldNomTeam = findViewById(R.id.fieldNomTeam);
        String strNomTeam = fieldNomTeam.getText().toString();
        EditText fieldImageTeam = findViewById(R.id.fieldImageTeam);
        String strfieldImageTeam = fieldImageTeam.getText().toString();
        if(teamAModifier == null){
            Team team = new Team(strNomTeam, strfieldImageTeam);
            teamViewModel.insert(team);
            int idTeam = teamViewModel.getTeamIdMax();
            team = teamViewModel.getTeamById(idTeam);
            for(Catcheur catcheur : catcheursSelected){
                teamViewModel.insertTeamWithCatcheursAsyncTask(catcheur, team);
            }
        }
        else{
            teamAModifier.setNomTeam(strNomTeam);
            teamAModifier.setImage(strfieldImageTeam);
            teamViewModel.update(teamAModifier);
            System.out.println("Update");
            System.out.println(teamAModifier.getTeamId());
            //Delete all insertion de team
            List<Catcheur> listeTmp = teamViewModel.getTeamWithCatcheursById(teamAModifier.getTeamId()).catcheurs;
            for(Catcheur catcheur : listeTmp){
                teamViewModel.deleteTeamWithCatcheursAsyncTask(catcheur, teamAModifier);
            }
            //Création des nouvelle relations
            for(Catcheur catcheur : catcheursSelected){
                teamViewModel.insertTeamWithCatcheursAsyncTask(catcheur, teamAModifier);
            }
        }
        goToShowTeams(view);
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
        intent.putExtra("teamAModifier", teamAModifier);
        startActivity(intent);
        finish();
    }

    public void goToShowTeams(View view){
        Intent intent = new Intent(this, ShowTeams.class);
        startActivity(intent);
        finish();
    }
}