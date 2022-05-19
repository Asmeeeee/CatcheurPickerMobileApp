package com.example.myrecyclerview2a2122.Model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taskTable")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String title;
    private String subtitle;
    private Integer priority;

    public Task(String title, String subtitle, Integer priority) {
        this.title = title;
        this.subtitle = subtitle;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
