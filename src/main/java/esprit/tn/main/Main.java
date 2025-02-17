package esprit.tn.main;

import java.util.Date;
import java.util.Scanner;
import esprit.tn.entities.Reclamation;
import esprit.tn.entities.Reponse;
import esprit.tn.services.ReclamationService;
import esprit.tn.services.ReponseService;
import esprit.tn.entities.Type;
import esprit.tn.main.DatabaseConnection;

import static javafx.application.Application.launch;

public class Main {
    public static void main(String[] args) {

        DatabaseConnection.getInstance();

        ReclamationService reclamationService = new ReclamationService();
        ReponseService reponseService = new ReponseService();

        Scanner scanner = new Scanner(System.in);

        System.out.println("*********** Menu Réclamation ****************");
        System.out.println("1. Ajouter une réclamation");
        System.out.println("2. Supprimer une réclamation");
        System.out.println("3. Modifier une réclamation");
        System.out.println("7. Afficher toutes les réclamations");

        System.out.println("*********** Menu Réponse ****************");
        System.out.println("4. Ajouter une réponse");
        System.out.println("5. Supprimer une réponse");
        System.out.println("6. Modifier une réponse");
        System.out.println("8. Afficher toutes les réponses");

        System.out.print("\nChoisissez une option : ");
        int choixAction = scanner.nextInt();
        scanner.nextLine();

        if (choixAction == 1) {
            System.out.print("Nom utilisateur : ");
            String nom_utilisateur = scanner.nextLine();

            System.out.print("Email : ");
            String email = scanner.nextLine();

            System.out.print("Description : ");
            String description = scanner.nextLine();

            System.out.println("Choisissez une catégorie : ");
            System.out.println("1. BUG_TECHNIQUE");
            System.out.println("2. PROBLEME_LOGISTIQUE");
            System.out.println("3. PROBLEME_PAIEMENT");
            System.out.println("4. PROBLEME_TRANSPORT");
            int choixCategorie = scanner.nextInt();
            scanner.nextLine();

           /* Type categorie = switch (choixCategorie) {
                case 1 -> Type.BUG_TECHNIQUE;
                case 2 -> Type.PROBLEME_LOGISTIQUE;
                case 3 -> Type.PROBLEME_PAIEMENT;
                case 4 -> Type.PROBLEME_TRANSPORT;
                default -> {
                    System.out.println("Choix invalide, catégorie par défaut : BUG_TECHNIQUE");
                    yield Type.BUG_TECHNIQUE;
                }
            };*/

           // Reclamation reclamation = new Reclamation(0, nom_utilisateur, email, new Date(), description, categorie);
           // reclamationService.ajouter(reclamation);
            System.out.println("Réclamation ajoutée avec succès !");

        } else if (choixAction == 2) {
            System.out.print("Entrez l'ID de la réclamation à supprimer : ");
            int idReclamation = scanner.nextInt();

            reclamationService.supprimer(idReclamation);
            System.out.println("Réclamation supprimée avec succès !");

        } else if (choixAction == 3) {
            System.out.print("Entrez l'ID de la réclamation à modifier : ");
            int idReclamation = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nouveau nom utilisateur : ");
            String nom_utilisateur = scanner.nextLine();

            System.out.print("Nouveau email : ");
            String email = scanner.nextLine();

            System.out.print("Nouvelle description : ");
            String description = scanner.nextLine();

            System.out.println("Choisissez une nouvelle catégorie : ");
            System.out.println("1. BUG_TECHNIQUE");
            System.out.println("2. PROBLEME_LOGISTIQUE");
            System.out.println("3. PROBLEME_PAIEMENT");
            System.out.println("4. PROBLEME_TRANSPORT");
            int choixCategorie = scanner.nextInt();
            scanner.nextLine();

            Type categorie = switch (choixCategorie) {
                case 1 -> Type.BUG_TECHNIQUE;
                case 2 -> Type.PROBLEME_LOGISTIQUE;
                case 3 -> Type.PROBLEME_PAIEMENT;
                case 4 -> Type.PROBLEME_TRANSPORT;
                default -> {
                    System.out.println("Choix invalide, catégorie par défaut : BUG_TECHNIQUE");
                    yield Type.BUG_TECHNIQUE;
                }
            };

           // Reclamation reclamation = new Reclamation(idReclamation, nom_utilisateur, email, new Date(), description, categorie);
            //reclamationService.modifier(reclamation);
            System.out.println("Réclamation modifiée avec succès !");

        } else if (choixAction == 4) {
            System.out.print("Entrez l'ID de la réclamation pour la réponse : ");
            int idReclamation = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Entrez votre réponse : ");
            String reponse = scanner.nextLine();

            Reponse reponseObj = new Reponse(0, idReclamation, reponse);
            reponseService.ajouter(reponseObj);
            System.out.println("Réponse ajoutée avec succès !");

        } else if (choixAction == 5) {
            System.out.print("Entrez l'ID de la réponse à supprimer : ");
            int idReponse = scanner.nextInt();

            reponseService.supprimer(idReponse);
            System.out.println("Réponse supprimée avec succès !");

        } else if (choixAction == 6) {
            System.out.print("Entrez l'ID de la réponse à modifier : ");
            int idReponse = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Entrez la nouvelle réponse : ");
            String nouvelleReponse = scanner.nextLine();

            Reponse reponseObj = new Reponse(idReponse, 0, nouvelleReponse);
            reponseService.modifier(reponseObj);
            System.out.println("Réponse modifiée avec succès !");

        } else if (choixAction == 7) {
            reclamationService.afficher();

        } else if (choixAction == 8) {
            reponseService.afficher();

        } else {
            System.out.println("Option invalide. Veuillez choisir une option valide.");
        }

        scanner.close();
    }
}












