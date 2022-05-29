package fr.maxime.catcheurpicker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "teamTable")
public class Team implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int teamId;
    private String nomTeam;
    private String image;

    public Team(String nomTeam, String image) {
        this.nomTeam = nomTeam;
        this.image = image;
    }

    protected Team(Parcel in) {
        teamId = in.readInt();
        nomTeam = in.readString();
        image = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.teamId);
        dest.writeString(this.nomTeam);
        dest.writeString(this.image);
    }
}
