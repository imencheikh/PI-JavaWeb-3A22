package esprit.tn.entities;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Objects;

public class Events {
    private int idEvent;
    private String nomEv;
    private String description;
    private Date dateEvent;
    private String nomSp;
    public Events(){}
    public Events(int idEvent,String nomEv,String description,Date dateEvent, String nomSp)
    {
        this.idEvent=idEvent;
        this.nomEv=nomEv;
        this.description=description;
        this.dateEvent=dateEvent;
        this.nomSp=nomSp;

    }
    public Events(String nomEv,String description,Date dateEvent)
    {

        this.nomEv=nomEv;
        this.description=description;
        this.dateEvent=dateEvent;

    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getNomEv() {
        return nomEv;
    }

    public void setNomEv(String nomEv) {
        this.nomEv = nomEv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Date dateEvent) {
        this.dateEvent = dateEvent;
    }

    public void setNomSp(String nomSp) {
        this.nomSp = nomSp;
    }

    public String getNomSp() {
        return nomSp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Events ev)) return false;
        return idEvent == ev.idEvent && Objects.equals(nomEv, ev.nomEv);

    }
    @Override
    public int hashCode() {
        return Objects.hash(idEvent, nomEv, description,dateEvent);
    }


    @Override
    public String toString() {
        return "Event{" +
                "Id=" + idEvent +
                ", Nom de l'évenement='" + nomEv + '\'' +
                ", Description='" + description + '\'' +
                ", Date de l'évenement='" + dateEvent + '\'' +

                "} \n";
    }
}
