package ca.cal.tp2.Modele;

import lombok.Data;

import java.util.Date;

@Data
public class EmpruntDetail {
    private final long lineItemID;
    private final Date dateRetourPrevue;
    private Date dateRetourActuelle;
    private String status;

    public EmpruntDetail(long lineItemID, Date dateRetourPrevue) {
        this.lineItemID = lineItemID;
        this.dateRetourPrevue = dateRetourPrevue;
        this.status = "en cours";
    }

    public boolean isEnRetard() {
        if (dateRetourActuelle != null) {
            return dateRetourActuelle.after(dateRetourPrevue);
        }
        return false;
    }
}