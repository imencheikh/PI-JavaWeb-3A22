package esprit.tn.entities;

import java.util.Objects;

public class Sponsors {
    private int idsponsor;
    private String nomSponsor;
    private String emailSpon;
    private float contribution;
    public Sponsors(){}
    public Sponsors(int idsponsor,String nomSponsor,String emailSpon,float contribution){
        this.idsponsor=idsponsor;
        this.nomSponsor=nomSponsor;
        this.emailSpon=emailSpon;
        this.contribution=contribution;
    }
    public Sponsors(String nomSponsor,String emailSpon,float contribution){

        this.nomSponsor=nomSponsor;
        this.emailSpon=emailSpon;
        this.contribution=contribution;
    }

    public int getIdsponsor() {
        return idsponsor;
    }

    public void setIdsponsor(int idsponsor) {
        this.idsponsor = idsponsor;
    }

    public String getNomSponsor() {
        return nomSponsor;
    }

    public void setNomSponsor(String nomSponsor) {
        this.nomSponsor = nomSponsor;
    }

    public String getEmailSpon() {
        return emailSpon;
    }

    public void setEmailSpon(String emailSpon) {
        this.emailSpon = emailSpon;
    }

    public float getContribution() {
        return contribution;
    }

    public void setContribution(float contribution) {
        this.contribution = contribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sponsors sp)) return false;
        return idsponsor == sp.idsponsor && Objects.equals(nomSponsor, sp.nomSponsor);

    }
    @Override
    public int hashCode() {
        return Objects.hash(idsponsor, nomSponsor, emailSpon,contribution);
    }


    @Override
    public String toString() {
        return "Sponsors{" +
                "Id=" + idsponsor +
                ", Nom de Sponsor='" + nomSponsor + '\'' +
                ", Email='" + emailSpon + '\'' +
                ", contribution de sponsor='" + contribution + '\'' +

                "} \n";
    }
}
