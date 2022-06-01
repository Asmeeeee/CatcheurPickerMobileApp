package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.CatcheurWithTeams;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamWithCatcheurs;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class ShowCatcheurs extends AppCompatActivity {
    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheur customAdapterCatcheur;
    private CatcheurViewModel catcheurViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_catcheurs);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        RecyclerView recyclerViewCatcheurs = findViewById(R.id.recyclerViewCatcheurs);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheur = new CustomAdapterCatcheur(dataCatcheur);
        CustomAdapterCatcheur.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) throws ExecutionException, InterruptedException {
                Log.d("MesLogs","onItemClick MainActivity");
                Catcheur catcheur = dataCatcheur.get(position);
                CatcheurWithTeams catcheurWithTeams = catcheurViewModel.getCatcheurWithTeamsById(catcheur.getCatcheurId());
                new AlertDialog.Builder(v.getContext())
                        .setTitle(catcheur.getNomScene())
                        .setMessage("Poids: "+catcheur.getTaille() + " Taille : "+catcheur.getTaille()+"\nTeams associées: "+ catcheurWithTeams.teams)
                        .show();
            }
            @Override
            public void onItemLongClick(int position, View view) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(100);
                catcheurViewModel.deleteOneCatcheur(dataCatcheur.get(position));
            }

            @Override
            public void onItemClickDelete(int position, View v) {
                Catcheur catcheur = dataCatcheur.get(position);
                catcheurViewModel.deleteOneCatcheur(catcheur);
                Vibrator v2 = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v2.vibrate(100);
            }

            @Override
            public void onItemModifier(int position, View v) {
                Catcheur catcheur = dataCatcheur.get(position);
                goToAddCatcheurForModifier(v, catcheur);
            }
        });
        recyclerViewCatcheurs.setAdapter(customAdapterCatcheur);
        recyclerViewCatcheurs.setLayoutManager(linearLayoutManager);


        catcheurViewModel.getNbCatcheursLD().observe(this, new Observer<Integer>(){
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.textViewNbLD);
                textView.setText("Nombre de Catcheur : "+ integer);
            }
        });
        catcheurViewModel.getAllCatcheursLD().observe(this, new Observer<List<Catcheur>>() {
            @Override
            public void onChanged(List<Catcheur> catcheurs) {
                dataCatcheur = catcheurs;
                customAdapterCatcheur.setData(catcheurs);
                customAdapterCatcheur.notifyDataSetChanged();
            }
        });
    }

    public void searchCatcheur(View view) throws ExecutionException, InterruptedException {
        EditText fieldRechercheCatcheur = findViewById(R.id.fieldRechercheCatcheur);
        String strFieldRechercheCatcheur = fieldRechercheCatcheur.getText().toString();
        List<Catcheur> listeFilter = new ArrayList<>();
        listeFilter = catcheurViewModel.searchCatcheur(strFieldRechercheCatcheur);
        customAdapterCatcheur.setData(listeFilter);
        customAdapterCatcheur.notifyDataSetChanged();
    }

    public void goToAddCatcheurForModifier(View view, Catcheur catcheur){
        Intent intent = new Intent(this, AddCatcheur.class);
        intent.putExtra("catcheurAModifier", catcheur);
        startActivity(intent);
        finish();
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