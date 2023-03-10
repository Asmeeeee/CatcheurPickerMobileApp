package fr.maxime.catcheurpicker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheursSelected;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class LinkCatcheursToTeam extends AppCompatActivity {
    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private List<Catcheur> catcheursSelected = new ArrayList<>();
    private Team teamAModifier;
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheursSelected customAdapterCatcheursSelected;
    private CatcheurViewModel catcheurViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.link_catcheurs_to_team);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        RecyclerView recyclerViewLinkCatcheursToTeam = findViewById(R.id.recyclerViewLinkCatcheursToTeam);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheursSelected = new CustomAdapterCatcheursSelected(dataCatcheur);
        try {
            Bundle bundle = getIntent().getExtras();
            teamAModifier = (Team) bundle.get("teamAModifier");
            System.out.println("Recoi la teamModifier");
            System.out.println(teamAModifier);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        CustomAdapterCatcheursSelected.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Catcheur catcheur = dataCatcheur.get(position);
                if(!catcheursSelected.contains(catcheur)){
                    catcheursSelected.add(catcheur);
                }
                else{
                    catcheursSelected.remove(catcheur);
                }
            }
            @Override
            public void onItemLongClick(int position, View view) {
                catcheurViewModel.deleteOneCatcheur(dataCatcheur.get(position));
            }

            @Override
            public void onItemClickDelete(int position, View v) {

            }

            @Override
            public void onItemModifier(int position, View v) {

            }
        });

        recyclerViewLinkCatcheursToTeam.setAdapter(customAdapterCatcheursSelected);
        recyclerViewLinkCatcheursToTeam.setLayoutManager(linearLayoutManager);

        catcheurViewModel.getAllCatcheursLD().observe(this, new Observer<List<Catcheur>>() {
            @Override
            public void onChanged(List<Catcheur> catcheurs) {
                dataCatcheur = catcheurs;
                customAdapterCatcheursSelected.setData(catcheurs);
                customAdapterCatcheursSelected.notifyDataSetChanged();
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
        intent.putParcelableArrayListExtra("catcheursSelected", (ArrayList<? extends Parcelable>) catcheursSelected);
        intent.putExtra("teamAModifier", teamAModifier);
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
