package fr.maxime.catcheurpicker.Model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class CatcheurWithTeams {
    @Embedded public Catcheur catcheur;
    @Relation(
            parentColumn = "catcheurId",
            entityColumn = "teamId",
            associateBy = @Junction(TeamCatcheurCrossRef.class)
    )
    public List<Team> teams;
}
