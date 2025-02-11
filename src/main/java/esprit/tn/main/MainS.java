
package esprit.tn.main;

import esprit.tn.entities.Sponsors;
import esprit.tn.services.SponsorService;

import java.util.Scanner;

public class MainS {
    public static void main(String[] args) {
        DatabaseConnection.getInstance(); // Assurez-vous d'avoir la bonne méthode pour initialiser la connexion
        SponsorService sponsorService = new SponsorService(); // Service qui gère les sponsors
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU SPONSORS =====");
            System.out.println("1. Ajouter un sponsor");
            System.out.println("2. Modifier un sponsor");
            System.out.println("3. Supprimer un sponsor");
            System.out.println("4. Afficher tous les sponsors");
            System.out.println("5. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne vide après un int

            switch (choix) {
                case 1:
                    // Ajouter un sponsor
                    System.out.print("Entrez le nom du sponsor : ");
                    String nomSponsor = scanner.nextLine();
                    System.out.print("Entrez l'email du sponsor : ");
                    String emailSpon = scanner.nextLine();
                    System.out.print("Entrez la contribution du sponsor : ");
                    float contribution = scanner.nextFloat();
                    scanner.nextLine(); // Consommer la ligne vide après un float

                    // Création du sponsor
                    Sponsors newSponsor = new Sponsors(nomSponsor, emailSpon, contribution);
                    sponsorService.ajouter(newSponsor);  // Appel au service pour ajouter le sponsor

                    System.out.println("Sponsor ajouté avec succès !");
                    break;

                case 2:
                    // Modifier un sponsor
                    System.out.println("Entrez l'ID du sponsor à modifier :");
                    int idSponsorModif = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne vide

                    // Vérifier si le sponsor existe
                    Sponsors sponsorExist = sponsorService.getOne(idSponsorModif);
                    if (sponsorExist == null) {
                        System.out.println("Le sponsor avec l'ID " + idSponsorModif + " n'existe pas.");
                    } else {
                        // Demander les nouvelles informations
                        System.out.println("Entrez le nouveau nom du sponsor (laisser vide pour ne pas changer) :");
                        String newNomSponsor = scanner.nextLine();

                        System.out.println("Entrez le nouvel email du sponsor (laisser vide pour ne pas changer) :");
                        String newEmailSpon = scanner.nextLine();

                        System.out.println("Entrez la nouvelle contribution du sponsor (laisser vide pour ne pas changer) :");
                        String newContributionStr = scanner.nextLine();
                        float newContribution = sponsorExist.getContribution();  // Garder l'ancienne valeur si vide

                        if (!newContributionStr.isEmpty()) {
                            try {
                                newContribution = Float.parseFloat(newContributionStr);  // Convertir en float
                            } catch (NumberFormatException e) {
                                System.out.println("Contribution invalide !");
                                return;
                            }
                        }

                        // Création de l'objet Sponsor avec les nouvelles informations
                        Sponsors sponsorModif = new Sponsors(
                                newNomSponsor.isEmpty() ? sponsorExist.getNomSponsor() : newNomSponsor,
                                newEmailSpon.isEmpty() ? sponsorExist.getEmailSpon() : newEmailSpon,
                                newContribution
                        );

                        sponsorModif.setIdsponsor(idSponsorModif);
                        sponsorService.modifier(sponsorModif);  // Modifier le sponsor dans le service
                        System.out.println("Sponsor modifié avec succès !");
                    }
                    break;

                case 3:
                    // Supprimer un sponsor
                    System.out.println("Entrez l'ID du sponsor à supprimer :");
                    int idSponsorSupp = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne vide

                    Sponsors sponsorSupp = new Sponsors();
                    sponsorSupp.setIdsponsor(idSponsorSupp);
                    sponsorService.supprimer(sponsorSupp);  // Supprimer le sponsor dans le service

                    //System.out.println("Sponsor supprimé avec succès !");
                    break;

                case 4:
                    // Afficher tous les sponsors
                    System.out.println("Liste de tous les sponsors :");
                    sponsorService.getAll();
                    break;

                case 5:
                    System.out.println("Programme terminé.");
                    return;

                default:
                    System.out.println("Option invalide. Réessayez !");
            }
        }
    }
}
