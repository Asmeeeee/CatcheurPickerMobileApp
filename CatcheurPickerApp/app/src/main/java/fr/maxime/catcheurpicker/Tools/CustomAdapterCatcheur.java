package fr.maxime.catcheurpicker.Tools;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.AddCatcheur;
import fr.maxime.catcheurpicker.LinkCatcheursToTeam;
import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.R;
import fr.maxime.catcheurpicker.ShowCatcheurs;

public class CustomAdapterCatcheur extends RecyclerView.Adapter<CustomAdapterCatcheur.MyViewHolder> {
    private List<Catcheur> data;
    private static InterfaceGestionClick myGestionClick;

    public CustomAdapterCatcheur(List<Catcheur> data) {
        this.data = data;
    }

    public static void setMyGestionClick(InterfaceGestionClick myGestionClick) {
        CustomAdapterCatcheur.myGestionClick = myGestionClick;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvNomScene, tvPoids, tvTaille, tvDateNaissance;
        private ImageView imageCatcheur;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvNomScene = itemView.findViewById(R.id.textViewNomScene);
            tvPoids = itemView.findViewById(R.id.textViewPoids);
            tvTaille = itemView.findViewById(R.id.textViewTaille);
            tvDateNaissance = itemView.findViewById(R.id.textViewDateNaissance);
            imageCatcheur = itemView.findViewById(R.id.ImageCatcheur);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.findViewById(R.id.delCatcheur).setOnClickListener(view -> {
                myGestionClick.onItemClickDelete(getAdapterPosition(), itemView);
            });
            itemView.findViewById(R.id.modifierCatcheur).setOnClickListener(view -> {
                myGestionClick.onItemModifier(getAdapterPosition(), itemView);
            });
        }

        public void display(Catcheur c){
            tvNomScene.setText(c.getNomScene()+"");
            tvPoids.setText("P??se : " + c.getPoids() + "kg");
            tvTaille.setText("Mesure : " + c.getTaille()+"m");
            tvDateNaissance.setText("N?? le " + c.getDateNaissance());
            Glide.with(itemView).load("https://icon-library.com/images/loading-icon-animated-gif/loading-icon-animated-gif-15.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).into(imageCatcheur);

        }

        @Override
        public void onClick(View v) {
            Log.d("MesLogs","onClick ViewHolder");
            try {
                myGestionClick.onItemClick(getAdapterPosition(),v);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            myGestionClick.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catcheur,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Catcheur catcheur = data.get(position);
        holder.display(catcheur);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Catcheur> list){ this.data = list;}

}
