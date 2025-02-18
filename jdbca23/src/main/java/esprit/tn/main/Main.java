package esprit.tn.main;

import esprit.tn.entities.Reservation;
import esprit.tn.services.ReservationService;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        // Initialize the database connection
        DatabaseConnection.getInstance();


        // Reservation CRUD demonstration
        ReservationService reservationService = new ReservationService();
        Reservation reservation = new Reservation(1, 1, LocalDateTime.now(), 1);

        // Add a reservation
        reservationService.ajouter(reservation);

        // Retrieve and print all reservations
        System.out.println("All reservations:");
        reservationService.getall().forEach(System.out::println);
    }
}
