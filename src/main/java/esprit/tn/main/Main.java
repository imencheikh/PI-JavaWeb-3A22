package esprit.tn.main;
import esprit.tn.entities.Events;
import esprit.tn.entities.Sponsors;
import esprit.tn.services.EventService;
import esprit.tn.entities.Sponsors;
import esprit.tn.services.SponsorService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection.getInstance();
        EventService ev=new EventService();
        SponsorService sponsorService = new SponsorService();
        Scanner scanner =new Scanner(System.in);
        while (true) {
            System.out.println("\n===== MENU Evenement=====");
            System.out.println("1. Ajouter un evenement");
            System.out.println("2. Modifier un evenement");
            System.out.println("3. Supprimer un evenement");
            System.out.println("4. Afficher toutes les evenements");
            System.out.println("\n===== MENU SPONSORS =====");
            System.out.println("5. Ajouter un sponsor");
            System.out.println("6. Modifier un sponsor");
            System.out.println("7. Supprimer un sponsor");
            System.out.println("8. Afficher tous les sponsors");
            System.out.println("9. Quitter");
            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consommer la ligne vide

            switch (choix) {
                case 1:
                    System.out.print("Entrez le nom : ");
                    String nomEv = scanner.nextLine();
                    System.out.print("Entrez une description : ");
                    String description = scanner.nextLine();

                    System.out.print("Date de l'événement (format YYYY-MM-DD) : ");
                    String dateEventStr = scanner.nextLine();

                    try {
                        // Conversion de String vers java.sql.Date
                        java.sql.Date dateEvent = java.sql.Date.valueOf(dateEventStr);

                        // Création de l'événement avec la date convertie
                        Events E = new Events(nomEv, description, dateEvent);
                        ev.ajouter(E);

                        System.out.println("Événement ajouté avec succès !");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Format de date invalide ! Utilisez le format YYYY-MM-DD.");
                    }
                    break;

                case 2:
                    System.out.println("Entrez l'ID de l'événement à modifier :");
                    int idModif = scanner.nextInt();
                    scanner.nextLine();  // Consommer la ligne restante

// Vérifier si l'ID existe dans la base de données avant la modification
                    Events eventExist = ev.getOne(idModif); // Utilisez la méthode getOne pour vérifier l'existence de l'événement par ID

                    if (eventExist == null) {
                        System.out.println("L'événement avec l'ID " + idModif + " n'existe pas.");
                    } else {
                        // Si l'événement existe, demandez les nouvelles informations
                        System.out.println("Entrez le nouveau nom de l'événement (laisser vide pour ne pas changer) :");
                        String newNomEv = scanner.nextLine();

                        System.out.println("Entrez une nouvelle description (laisser vide pour ne pas changer) :");
                        String newDescription = scanner.nextLine();

                        System.out.println("Entrez la nouvelle date (format YYYY-MM-DD, laisser vide pour ne pas changer) :");
                        String newDateStr = scanner.nextLine();

                        // Création de l'objet Events avec l'ID et les nouvelles valeurs
                        Events EModif = new Events();
                        EModif.setIdEvent(idModif);
                        EModif.setNomEv(newNomEv.isEmpty() ? null : newNomEv);
                        EModif.setDescription(newDescription.isEmpty() ? null : newDescription);

                        // Conversion de la date si elle est renseignée
                        if (!newDateStr.isEmpty()) {
                            try {
                                java.sql.Date newDate = java.sql.Date.valueOf(newDateStr); // Convertir String en java.sql.Date
                                EModif.setDateEvent(newDate);
                            } catch (IllegalArgumentException e) {
                                System.out.println("Format de date invalide. Veuillez utiliser YYYY-MM-DD.");
                                break; // Sortir du cas si la date est invalide
                            }
                        }

                        // Appel de la méthode de modification
                        ev.modifier(EModif);
                    }

                    break;

                case 3:
                    System.out.println("Entrez l'ID de l'evenement à supprimer :");
                    int idSupp = scanner.nextInt();
                    Events ESupp = new Events();
                    ESupp.setIdEvent(idSupp);
                    ev.supprimer(ESupp);
                   // System.out.println("Evenement supprimée !");
                    break;

                case 4:
                    System.out.println("Liste des evenements enregistrées :");
                    ev.getAll();
                    break;
                case 5:
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
                case 6:
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
                case 7:
                    // Supprimer un sponsor
                    System.out.println("Entrez l'ID du sponsor à supprimer :");
                    int idSponsorSupp = scanner.nextInt();
                    scanner.nextLine(); // Consommer la ligne vide

                    Sponsors sponsorSupp = new Sponsors();
                    sponsorSupp.setIdsponsor(idSponsorSupp);
                    sponsorService.supprimer(sponsorSupp);  // Supprimer le sponsor dans le service

                    break;
                case 8:
                    // Afficher tous les sponsors
                    System.out.println("Liste de tous les sponsors :");
                    sponsorService.getAll();
                    break;
                case 9:
                    System.out.println("Programme terminé.");
                    return;

                default:
                    System.out.println("Option invalide. Réessayez !");
            }
        }
    }
}