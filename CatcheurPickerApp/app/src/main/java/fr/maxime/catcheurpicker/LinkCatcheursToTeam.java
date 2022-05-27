package fr.maxime.catcheurpicker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

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
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class LinkCatcheursToTeam extends AppCompatActivity {
    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheur customAdapterCatcheur;
    private CustomAdapterTeam customAdapterTeam;
    private CatcheurViewModel catcheurViewModel;
    private TeamViewModel teamViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("j'arrive dans le LinkCatcheursToTeam");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.link_catcheurs_to_team);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        RecyclerView recyclerViewLinkCatcheursToTeam = findViewById(R.id.recyclerViewLinkCatcheursToTeam);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheur = new CustomAdapterCatcheur(dataCatcheur);
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
        recyclerViewLinkCatcheursToTeam.setAdapter(customAdapterCatcheur);
        recyclerViewLinkCatcheursToTeam.setLayoutManager(linearLayoutManager);

        catcheurViewModel.getAllCatcheursLD().observe(this, new Observer<List<Catcheur>>() {
            @Override
            public void onChanged(List<Catcheur> catcheurs) {
                dataCatcheur = catcheurs;
                customAdapterCatcheur.setData(catcheurs);
                customAdapterCatcheur.notifyDataSetChanged();
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
