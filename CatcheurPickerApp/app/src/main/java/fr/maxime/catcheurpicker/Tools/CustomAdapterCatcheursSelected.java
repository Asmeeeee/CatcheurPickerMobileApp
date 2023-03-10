package fr.maxime.catcheurpicker.Tools;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.R;

public class CustomAdapterCatcheursSelected extends RecyclerView.Adapter<CustomAdapterCatcheursSelected.MyViewHolder> {
    private List<Catcheur> data;
    private static InterfaceGestionClick myGestionClick;

    public CustomAdapterCatcheursSelected(List<Catcheur> data) {
        this.data = data;
    }

    public static void setMyGestionClick(InterfaceGestionClick myGestionClick) {
        CustomAdapterCatcheursSelected.myGestionClick = myGestionClick;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvNomScene, tvPoids, tvTaille, tvImage, tvDateNaissance;


        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvNomScene = itemView.findViewById(R.id.textViewNomScene);
            tvPoids = itemView.findViewById(R.id.textViewPoids);
            tvTaille = itemView.findViewById(R.id.textViewTaille);
            tvDateNaissance = itemView.findViewById(R.id.textViewDateNaissance);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void display(Catcheur c){
            tvNomScene.setText(c.getNomScene());
            tvPoids.setText(c.getPoids()+"");
            tvTaille.setText(c.getTaille()+"");
            tvDateNaissance.setText(c.getDateNaissance());
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
    public CustomAdapterCatcheursSelected.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_catcheurs_selected,parent,false);
        return new CustomAdapterCatcheursSelected.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomAdapterCatcheursSelected.MyViewHolder holder, int position) {
        Catcheur catcheur = data.get(position);
        holder.display(catcheur);
    }

    @Override
    public int getItemCount() {
        try {
            return data.size();
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void setData(List<Catcheur> list){ this.data = list;}
}
