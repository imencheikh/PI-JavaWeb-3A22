package esprit.tn.entities;

import java.util.Objects;

public class Offre {
    private int id_offre;
    private float prix_offre;
    private String description_offre;
    private int id_event;

    public Offre() {}

    public Offre(int id_offre, float prix_offre, String description_offre, int id_event) {
        this.id_offre = id_offre;
        this.prix_offre = prix_offre;
        this.description_offre = description_offre;
        this.id_event = id_event;
    }

    public Offre(float prix_offre, String description_offre, int id_event) {
        this.prix_offre = prix_offre;
        this.description_offre = description_offre;
        this.id_event = id_event;
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public float getPrix_offre() {
        return prix_offre;
    }

    public void setPrix_offre(float prix_offre) {
        this.prix_offre = prix_offre;
    }

    public String getDescription_offre() {
        return description_offre;
    }

    public void setDescription_offre(String description_offre) {
        this.description_offre = description_offre;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Offre offre)) return false;
        return id_offre == offre.id_offre &&
                Float.compare(offre.prix_offre, prix_offre) == 0 &&
                id_event == offre.id_event &&
                Objects.equals(description_offre, offre.description_offre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_offre, prix_offre, description_offre, id_event);
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id_offre=" + id_offre +
                ", prix_offre=" + prix_offre +
                ", description_offre='" + description_offre + '\'' +
                ", id_event=" + id_event +
                '}';
    }
}
