package esprit.tn.entities;

import java.util.Date;
import java.util.Objects;

public class Reponse {
    private int id_reponse;
    private int id_reclamation;
    private String reponse;


    // 🔹 Constructeur par défaut
    public Reponse() {
    }

    // 🔹 Constructeur complet
    public Reponse(int id_reponse, int id_reclamation, String reponse) {
        this.id_reponse = id_reponse;
        this.id_reclamation = id_reclamation;
        this.reponse = reponse;

    }

    // ✅ 🔹 Nouveau constructeur adapté au contrôleur
    public Reponse(int id_reclamation, String reponse) {
        this.id_reclamation = id_reclamation;
        this.reponse = reponse;

    }

    // 🔹 Getters et Setters
    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }



    // 🔹 Méthode equals pour comparer les objets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reponse)) return false;
        Reponse reponse1 = (Reponse) o;
        return id_reponse == reponse1.id_reponse &&
                id_reclamation == reponse1.id_reclamation &&
                Objects.equals(reponse, reponse1.reponse) ;

    }

    // 🔹 Méthode hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id_reponse, id_reclamation, reponse);
    }

    // 🔹 Méthode toString pour affichage
    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", reponse='" + reponse + '\'' +

                '}';
    }
}

