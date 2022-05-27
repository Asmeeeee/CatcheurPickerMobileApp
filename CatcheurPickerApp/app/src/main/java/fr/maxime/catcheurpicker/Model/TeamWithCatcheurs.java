package fr.maxime.catcheurpicker.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TeamWithCatcheurs {
    @Embedded public Team team;
    @Relation(
            parentColumn = "teamId",
            entityColumn = "catcheurId",
            associateBy = @Junction(TeamCatcheurCrossRef.class)
    )
    public List<Catcheur> catcheurs;
}
