package fr.maxime.catcheurpicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Transaction;

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
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeam;
import fr.maxime.catcheurpicker.Tools.CustomAdapterTeamSelected;
import fr.maxime.catcheurpicker.Tools.InterfaceGestionClick;

public class AddCatcheur extends AppCompatActivity {

    private List<Catcheur> dataCatcheur = new ArrayList<>();
    private List<Team> teamsSelected = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapterCatcheur customAdapterCatcheur;
    private CustomAdapterTeamSelected customAdapterTeamSelected;
    private CatcheurViewModel catcheurViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_catcheur);
        catcheurViewModel = new ViewModelProvider(this).get(CatcheurViewModel.class);
        // RecyclerView recyclerView = findViewById(R.id.recyclerview); pour la liste de catcheur ou team
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapterCatcheur = new CustomAdapterCatcheur(dataCatcheur);
        customAdapterTeamSelected = new CustomAdapterTeamSelected(teamsSelected);

        try {
            Bundle bundle = this.getIntent().getExtras();
            teamsSelected = (List<Team>) bundle.get("teamsSelected");
            customAdapterTeamSelected.setData(teamsSelected);
            customAdapterTeamSelected.notifyDataSetChanged();
        }
        catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }

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
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(100);
            catcheurViewModel.deleteOneCatcheur(dataCatcheur.get(position));
        }
    });
}
        public void addCatcheur(View view) throws ExecutionException, InterruptedException {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
        EditText fieldNomCatcheur = findViewById(R.id.fieldNomCatcheur);
        String strNomCatcheur = fieldNomCatcheur.getText().toString();
        if(strNomCatcheur.equals("")){
            strNomCatcheur = "undifined";
        }
        EditText fieldPoidsCatcheur = findViewById(R.id.fieldPoids);
        int intPoidsCatcheur = 0;
        try{
            intPoidsCatcheur = Integer.parseInt(fieldPoidsCatcheur.getText().toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        EditText fieldTailleCatcheur = findViewById(R.id.fieldTaille);
        Float floatTailleCatcheur = 0.0f;
        try{
              Float.parseFloat(fieldTailleCatcheur.getText().toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        EditText fieldDateNaissance = findViewById(R.id.fieldDateNaiss);
        String strDateNaissance =  fieldDateNaissance.getText().toString();
        if(strDateNaissance.equals("")){
            strDateNaissance = " / / ";
        }
        EditText fieldImageCatcheur = findViewById(R.id.fieldImageCatcheur);
        String strImage = fieldImageCatcheur.getText().toString();
        if(strImage.equals("")){
            strImage = "https://images.emojiterra.com/google/noto-emoji/v2.034/128px/2754.png";
        }
        System.out.println(strNomCatcheur+" "+intPoidsCatcheur+" "+ floatTailleCatcheur+" "+ strDateNaissance);
        Catcheur catcheur = new Catcheur(strNomCatcheur, intPoidsCatcheur, floatTailleCatcheur, strImage, strDateNaissance);
        catcheurViewModel.insert(catcheur);
        int idCatcheur = catcheurViewModel.getCatcheurIdMax();
        catcheur = catcheurViewModel.getCatcheurById(idCatcheur);
        for(Team team : teamsSelected){
            catcheurViewModel.insertTeamWithCatcheursAsyncTask(catcheur, team);
        }
        goToShowCatcheurs(view);
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

    public void goToLinkTeamsToCatcheur(View view) {
        Intent intent = new Intent(this, LinkTeamsToCatcheur.class);
        startActivity(intent);
        finish();
    }
}