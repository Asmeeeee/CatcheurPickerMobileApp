package com.example.myrecyclerview2a2122.Tools;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrecyclerview2a2122.Model.Task;
import com.example.myrecyclerview2a2122.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private List<Task> data;
    private static  InterfaceGestionClick myGestionClick;

    public CustomAdapter(List<Task> data) {
        this.data = data;
    }

    public static void setMyGestionClick(InterfaceGestionClick myGestionClick) {
        CustomAdapter.myGestionClick = myGestionClick;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener,View.OnLongClickListener{
        private TextView tvTitle,tvSubtitle,tvPriority;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.textViewtitle);
            tvSubtitle = itemView.findViewById(R.id.textViewsubtitle);
            tvPriority = itemView.findViewById(R.id.textViewpriority);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void display(Task t){
            tvTitle.setText(t.getTitle());
            tvSubtitle.setText(t.getSubtitle());
            tvPriority.setText(t.getPriority().toString());
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


    //create news views
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlayout,parent,false);
        return new MyViewHolder(view);
    }

    //Replace the contents of a view
    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        Task task = data.get(position);
        holder.display(task);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Task> list){
        this.data = list;
    }
}
