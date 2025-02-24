package esprit.tn.entities;

import java.util.Objects;

public class Reponse {
    private int id_reponse;
    private int id_reclamation;
    private String reponse;
    private String nom_utilisateur;
    private String email;
    private String description;
    // 🔹 Constructeur par défaut
    public Reponse() {
    }

    // 🔹 Constructeur complet
    public Reponse(int id_reponse, int id_reclamation, String reponse, String nom_utilisateur) {
        this.id_reponse = id_reponse;
        this.id_reclamation = id_reclamation;
        this.reponse = reponse;
        this.nom_utilisateur = nom_utilisateur;
        this.email=email;
        this.description=description;
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

    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom_utilisateur) {
        this.nom_utilisateur = nom_utilisateur;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // 🔹 Méthode equals pour comparer les objets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reponse)) return false;
        Reponse reponse1 = (Reponse) o;
        return id_reponse == reponse1.id_reponse &&
                id_reclamation == reponse1.id_reclamation &&
                Objects.equals(reponse, reponse1.reponse) &&
                Objects.equals(email, reponse1.email) &&
                Objects.equals(nom_utilisateur, reponse1.nom_utilisateur);
    }

    // 🔹 Méthode hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id_reponse, id_reclamation, reponse, nom_utilisateur,email);
    }

    // 🔹 Méthode toString pour affichage
    @Override
    public String toString() {
        return "Reponse{" +
                "id_reponse=" + id_reponse +
                ", id_reclamation=" + id_reclamation +
                ", reponse='" + reponse + '\'' +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
