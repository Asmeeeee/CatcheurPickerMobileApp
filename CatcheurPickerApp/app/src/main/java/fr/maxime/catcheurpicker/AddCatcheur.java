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
    private Catcheur catcheurAModifier;
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

        try{
            Bundle bundle2 = this.getIntent().getExtras();
            catcheurAModifier = (Catcheur) bundle2.get("catcheurAModifier");
            //Merttre les valeur de team dans les champs
            EditText nomCatcheur = findViewById(R.id.fieldNomCatcheur);
            nomCatcheur.setText(catcheurAModifier.getNomScene());
            EditText taille = findViewById(R.id.fieldTaille);
            taille.setText(catcheurAModifier.getTaille()+"");
            EditText poids = findViewById(R.id.fieldPoids);
            poids.setText(catcheurAModifier.getPoids()+"");
            EditText dateNaissance = findViewById(R.id.fieldDateNaiss);
            dateNaissance.setText(catcheurAModifier.getDateNaissance());
            EditText urlImageCatcheur = findViewById(R.id.fieldImageCatcheur);
            urlImageCatcheur.setText(catcheurAModifier.getImage());
            //Fin des donn??e a jour
            if(teamsSelected == null){
                System.out.println("Liste remis comme avant");
                teamsSelected = catcheurViewModel.getCatcheurWithTeamsById(catcheurAModifier.getCatcheurId()).teams;
            }
            else{
                teamsSelected = (List<Team>) bundle2.get("teamsSelected");
            }
            customAdapterTeamSelected.setData(teamsSelected);
            customAdapterTeamSelected.notifyDataSetChanged();
        }
        catch (Exception exception){
            exception.printStackTrace();
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

            @Override
            public void onItemClickDelete(int position, View v) {

            }

            @Override
            public void onItemModifier(int position, View v) {

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
            floatTailleCatcheur = Float.parseFloat(fieldTailleCatcheur.getText().toString());
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
        //separation
        if(catcheurAModifier == null){
            Catcheur catcheur = new Catcheur(strNomCatcheur, intPoidsCatcheur, floatTailleCatcheur, strImage, strDateNaissance);
            catcheurViewModel.insert(catcheur);
            int idCatcheur = catcheurViewModel.getCatcheurIdMax();
            catcheur = catcheurViewModel.getCatcheurById(idCatcheur);
            for(Team team : teamsSelected){
                catcheurViewModel.insertTeamWithCatcheursAsyncTask(catcheur, team);
            }
        }
        else{
            catcheurAModifier.setNomScene(strNomCatcheur);
            catcheurAModifier.setPoids(intPoidsCatcheur);
            catcheurAModifier.setTaille(floatTailleCatcheur);
            catcheurAModifier.setImage(strImage);
            catcheurAModifier.setDateNaissance(strDateNaissance);
            catcheurViewModel.update(catcheurAModifier);
            //Delete all insertion de team
            List<Team> listeTmp = catcheurViewModel.getCatcheurWithTeamsById(catcheurAModifier.getCatcheurId()).teams;
            for(Team team : listeTmp){
                catcheurViewModel.deleteTeamWithCatcheursAsyncTask(catcheurAModifier, team);
            }
            //Cr??ation des nouvelle relations
            for(Team team : teamsSelected){
                catcheurViewModel.insertTeamWithCatcheursAsyncTask(catcheurAModifier, team);
            }
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
        intent.putExtra("catcheurAModifier", catcheurAModifier);
        startActivity(intent);
        finish();
    }
}