package fr.maxime.catcheurpicker.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "catcheurTable")
public class Catcheur implements Parcelable{
    @PrimaryKey(autoGenerate = true)
    private int catcheurId;
    private String nomScene;
    private int poids;
    private float taille;
    private String image;
    private String dateNaissance;

    public Catcheur(String nomScene, int poids, float taille, String image, String dateNaissance) {
        this.nomScene = nomScene;
        this.poids = poids;
        this.taille = taille;
        this.image = image;
        this.dateNaissance = dateNaissance;
    }

    protected Catcheur(Parcel in) {
        catcheurId = in.readInt();
        nomScene = in.readString();
        poids = in.readInt();
        taille = in.readFloat();
        image = in.readString();
        dateNaissance = in.readString();
    }

    public static final Creator<Catcheur> CREATOR = new Creator<Catcheur>() {
        @Override
        public Catcheur createFromParcel(Parcel in) {
            return new Catcheur(in);
        }

        @Override
        public Catcheur[] newArray(int size) {
            return new Catcheur[size];
        }
    };

    public int getCatcheurId() {
        return catcheurId;
    }

    public void setCatcheurId(int catcheurId) {
        this.catcheurId = catcheurId;
    }

    public String getNomScene() {
        return nomScene;
    }

    public void setNomScene(String nomScene) {
        this.nomScene = nomScene;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public float getTaille() {
        return taille;
    }

    public void setTaille(float taille) {
        this.taille = taille;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.catcheurId);
        dest.writeString(this.nomScene);
        dest.writeInt(this.poids);
        dest.writeFloat(this.taille);
        dest.writeString(this.image);
        dest.writeString(this.dateNaissance);
    }
}
