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

import java.util.ArrayList;
import java.util.List;

import fr.maxime.catcheurpicker.BD.CatcheurViewModel;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Tools.CustomAdapterCatcheur;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class AddCatcheur extends AppCompatActivity {

    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private List<Team> dataTeam = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheur customAdapterCatcheur;
    private CatcheurViewModel catcheurViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_catcheur);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        //RecyclerView recyclerView = findViewById(R.id.recyclerview); pour la liste de catcheur ou team
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheur = new CustomAdapterCatcheur(dataCatcheur);


        CustomAdapterCatcheur.setMyGestionClick(new InterfaceGestionClick() {
        @Override
        public void onItemClick(int position, View v) {
            Log.d("MesLogs","onItemClick ShowCatcheurs");
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
}

    public void addCatcheur(View view) {
        System.out.println("ICICICICICIICICICICI " + R.id.fieldNomCatcheur);
        catcheurViewModel.insert(new Catcheur(R.id.fieldNomCatcheur+"", R.id.fieldPoids, R.id.fieldTaille, "image", R.id.fieldNomCatcheur+""));
    }
//-------------------------------------------------------------------------------------

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