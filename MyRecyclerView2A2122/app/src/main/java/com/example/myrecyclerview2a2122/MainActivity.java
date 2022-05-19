package com.example.myrecyclerview2a2122;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myrecyclerview2a2122.BD.TaskViewModel;
import com.example.myrecyclerview2a2122.Model.Task;
import com.example.myrecyclerview2a2122.Tools.CustomAdapter;
import com.example.myrecyclerview2a2122.Tools.InterfaceGestionClick;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> data = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private CustomAdapter customAdapter;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        customAdapter = new CustomAdapter(data);
        CustomAdapter.setMyGestionClick(new InterfaceGestionClick() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d("MesLogs","onItemClick MainActivity");
                Task task = data.get(position);
                new AlertDialog.Builder(v.getContext())
                        .setTitle(task.getTitle())
                        .setMessage(task.getSubtitle() + " Priority : "+task.getPriority())
                        .show();
            }

            @Override
            public void onItemLongClick(int position, View view) {
                taskViewModel.deleteOneTask(data.get(position));
            }
        });
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        Button button = findViewById(R.id.buttonAddData);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<=20;i++){
                    //data.add(new Task("Title "+i,"Subtitle "+i,(i%5)+1));
                    taskViewModel.insert(new Task("task "+i,"subtitle "+i,
                            (i%5)+1));
                }
                //customAdapter.notifyDataSetChanged();
            }
        });
        Button buttondelete = findViewById(R.id.buttonDelete);
        buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskViewModel.delete();
            }
        });
        Button buttonupdate = findViewById(R.id.buttonMaj);
        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.textViewNb);
                Integer nb = taskViewModel.nbTask();
                textView.setText("nb task :" +nb);
            }
        });

        taskViewModel.getNbTaskLD().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                TextView textView = findViewById(R.id.textViewNbLD);
                textView.setText("nb Task LD: " + integer);
            }
        });

        taskViewModel.getAllTaskLD().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                data = tasks;
                customAdapter.setData(tasks);
                customAdapter.notifyDataSetChanged();
            }
        });
    }
}