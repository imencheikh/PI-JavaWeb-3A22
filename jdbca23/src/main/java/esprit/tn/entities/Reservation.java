package esprit.tn.entities;

import java.util.Objects;
import java.util.Date;

public class Reservation {
    private int id_reservation;
    private int id_user;
    private Date date_reservation;
    private int id_offre;

    public Reservation() {}

    public Reservation(int id_reservation, int id_user, Date date_reservation, int id_offre) {
        this.id_reservation = id_reservation;
        this.id_user = id_user;
        this.date_reservation = date_reservation;
        this.id_offre = id_offre;
    }

    public Reservation(int id_user, Date date_reservation, int id_offre) {
        this.id_user = id_user;
        this.date_reservation = date_reservation;
        this.id_offre = id_offre;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation reservation)) return false;
        return id_reservation == reservation.id_reservation &&
                id_user == reservation.id_user &&
                id_offre == reservation.id_offre &&
                Objects.equals(date_reservation, reservation.date_reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_reservation, id_user, date_reservation, id_offre);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id_reservation=" + id_reservation +
                ", id_user=" + id_user +
                ", date_reservation=" + date_reservation +
                ", id_offre=" + id_offre +
                '}';
    }
}
