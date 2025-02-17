package esprit.tn.services;
import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;

import java.util.List;
public interface Iservice <T> {

    public void ajouter(T t);
    public void modifier(T t);

    public void enregistrer(T t);
    public void supprimer(int idReclamation);

    public List<T> getall();

    List<T> getAll();


    public T getone();
    public void afficher();





}
