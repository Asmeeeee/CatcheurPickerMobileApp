package fr.maxime.catcheurpicker.Tools;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.maxime.catcheurpicker.BD.TeamRepository;
import fr.maxime.catcheurpicker.BD.TeamViewModel;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.R;
import fr.maxime.catcheurpicker.ShowTeams;

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
        private TextView tvNomTeam, tvImage;
        private CustomAdapterTeam customAdapterTeam;

        public MyViewHolder(@NonNull View itemView, CustomAdapterTeam customAdapterTeam){
            super(itemView);
            this.customAdapterTeam = customAdapterTeam;
            tvNomTeam = itemView.findViewById(R.id.textViewNomTeam);
            //tvImage = itemView.findViewById(R.id.textViewImage);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.findViewById(R.id.delTeam).setOnClickListener(view -> {
                myGestionClick.onItemClickDelete(getAdapterPosition(), itemView);
            });
            itemView.findViewById(R.id.modifierTeam).setOnClickListener(view -> {
                myGestionClick.onItemModifier(getAdapterPosition(), itemView);
            });
        }

        public void display(Team t){
            tvNomTeam.setText(t.getNomTeam());
            //tvImage.setText(t.getImage());
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
    public CustomAdapterTeam.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team,parent,false);
        return new MyViewHolder(view, this);
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
