package esprit.tn.entities;
import java.util.Date;
import java.util.Objects;



public class Reclamation {




    private int id_reclamation;
    private String nom_utilisateur;
    private String email;
    private Date date;
    private String description;
    private String categorie;

  //  public Reclamation(){}
    public Reclamation(String nom_utilisateur, String email, String description, String categorie, int id){
     this.nom_utilisateur=nom_utilisateur;
     this.email=email;
     this.description=description;
     this.categorie=categorie;

    }



public Reclamation(int id_reclamation, String nom_utilisateur, String email, Date date, String description, String categorie){
this.id_reclamation=id_reclamation;
this.nom_utilisateur=nom_utilisateur;
this.email=email;
this.date=date;
this.description=description;
this.categorie=categorie;

    }
    public int getId_reclamation() {
        return id_reclamation;
    }

    public void setId_reclamation(int id) {
        id_reclamation = id;
    }
    public String getNom_utilisateur() {
        return nom_utilisateur;
    }

    public void setNom_utilisateur(String nom){
        nom_utilisateur=nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie(){
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reclamation)) return false;
        Reclamation reclamation = (Reclamation) o;
        return id_reclamation == reclamation.id_reclamation &&
                Objects.equals(nom_utilisateur, reclamation.nom_utilisateur) &&
                Objects.equals(email, reclamation.email) &&
                Objects.equals(date, reclamation.date) &&
                Objects.equals(description, reclamation.description) &&
                categorie == reclamation.categorie; // Comparaison directe pour une énumération

    }

    public int hashCode() {
        return Objects.hash(id_reclamation, nom_utilisateur, email, date, description, categorie);
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "Id_reclamation=" + id_reclamation +
                ", nom_utilisateur='" + nom_utilisateur + '\'' +
                ", email='" + email + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", categorie=" + categorie +
                '}';
    }



}