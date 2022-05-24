package fr.maxime.catcheurpicker.Model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "teamTable")
public class Team {
    @PrimaryKey(autoGenerate = true)
    private int teamId;
    private String nomTeam;
    private String image;

    public Team(String nomTeam, String image) {
        this.nomTeam = nomTeam;
        this.image = image;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
