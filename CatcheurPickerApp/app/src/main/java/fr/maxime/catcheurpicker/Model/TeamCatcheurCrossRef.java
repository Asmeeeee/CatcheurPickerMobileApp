package fr.maxime.catcheurpicker.Model;

import androidx.room.Entity;

@Entity(primaryKeys = {"teamId", "catcheurId"})
public class TeamCatcheurCrossRef {

    public TeamCatcheurCrossRef(int teamId, int catcheurId){
        this.teamId = teamId;
        this.catcheurId = catcheurId;
    }

    private int teamId;
    private int catcheurId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getCatcheurId() {
        return catcheurId;
    }

    public void setCatcheurId(int catcheurId) {
        this.catcheurId = catcheurId;
    }
}
