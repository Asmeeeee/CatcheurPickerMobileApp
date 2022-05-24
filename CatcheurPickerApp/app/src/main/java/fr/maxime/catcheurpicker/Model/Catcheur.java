package fr.maxime.catcheurpicker.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "catcheurTable")
public class Catcheur {
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
}
