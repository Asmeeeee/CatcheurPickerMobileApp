package fr.maxime.catcheurpicker.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "teamTable")
public class Team {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nomTeam;
    private String image;

    public Team(String nomTeam, String image) {
        this.nomTeam = nomTeam;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomTeam() {
        return nomTeam;
    }

    public void setNomTeam(String nomTeam) {
        this.nomTeam = nomTeam;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
