package esprit.tn.entities;

import java.util.Date;
import java.util.Objects;

public class Reponse {
    private int id_reponse;
    private int id_reclamation;
    private String reponse;
    private Date date_reponse;


    public Reponse() {
    }


    public Reponse(int id_reponse, int id_reclamation, String reponse, Date date_reponse) {
        this.id_reponse = id_reponse;
        this.id_reclamation = id_reclamation;
        this.reponse = reponse;
        this.date_reponse = date_reponse;
    }


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

    public Date getDate_reponse() {
        return date_reponse;
    }

    public void setDate_reponse(Date date_reponse) {
        this.date_reponse = date_reponse;
    }

    // Méthode equals pour comparer les objets Reponse
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reponse)) return false;
        Reponse reponse1 = (Reponse) o;
        return id_reponse == reponse1.id_reponse &&
                id_reclamation == reponse1.id_reclamation &&
                Objects.equals(reponse, reponse1.reponse) &&
                Objects.equals(date_reponse, reponse1.date_reponse);
    }

    // Méthode hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id_reponse, id_reclamation, reponse, date_reponse);
    }

    // Méthode toString pour afficher les objets Reponse
    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", reponse='" + reponse + '\'' +
                ", date_reponse=" + date_reponse +
                '}';
    }
}
