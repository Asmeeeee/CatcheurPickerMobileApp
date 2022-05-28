package fr.maxime.catcheurpicker.Tools;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.maxime.catcheurpicker.BD.TeamRepository;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.R;

public class CustomAdapterTeam extends RecyclerView.Adapter<CustomAdapterTeam.MyViewHolder>{
    private List<Team> data;
    private static InterfaceGestionClick myGestionClick;

    public CustomAdapterTeam(List<Team> data) {
        this.data = data;
    }

    public static void setMyGestionClick(InterfaceGestionClick myGestionClick) {
        CustomAdapterTeam.myGestionClick = myGestionClick;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvNomTeam, tvImage, tvNbCatcheurs;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvNomTeam = itemView.findViewById(R.id.textViewNomTeam);
            tvImage = itemView.findViewById(R.id.textViewImage);
            tvNbCatcheurs = itemView.findViewById(R.id.textViewNbCatcheurs);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void display(Team t){
            tvNomTeam.setText(t.getNomTeam());
            tvImage.setText(t.getImage());
            //TeamRepository.getTeamsWithCatcheursByTeam(t.getTeamId());
            //tvNbCatcheurs.setText(t.get);
        }

        @Override
        public void onClick(View v) {
            Log.d("MesLogs","onClick ViewHolder");
            myGestionClick.onItemClick(getAdapterPosition(),v);
        }

        @Override
        public boolean onLongClick(View v) {
            myGestionClick.onItemLongClick(getAdapterPosition(),v);
            return false;
        }
    }

    @NonNull
    @Override
    public CustomAdapterTeam.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterTeam.MyViewHolder holder, int position) {
        Team team = data.get(position);
        holder.display(team);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Team> list){ this.data = list;}

}
