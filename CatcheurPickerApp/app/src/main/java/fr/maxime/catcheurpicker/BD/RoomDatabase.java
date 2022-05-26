package fr.maxime.catcheurpicker.BD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;


import fr.maxime.catcheurpicker.Model.Catcheur;
import fr.maxime.catcheurpicker.Model.Team;
import fr.maxime.catcheurpicker.Model.TeamCatcheurCrossRef;

@Database(entities = {Catcheur.class, Team.class, TeamCatcheurCrossRef.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase INSTANCE;

    public abstract CatcheurDao catcheurDao();

    public abstract  TeamDao teamDao();

    static RoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            System.out.println("Creation DATABASE");
            synchronized (RoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, "catcheur_database" ).build();
                    System.out.println("DATABSE Crée");
                    System.out.println(INSTANCE);
                }
            }
        }
        return INSTANCE;
    }
}
