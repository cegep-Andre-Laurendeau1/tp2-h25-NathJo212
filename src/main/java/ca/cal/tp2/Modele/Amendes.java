package ca.cal.tp2.Modele;

import lombok.Data;

import java.util.Date;

@Data
public class Amendes {
    private final long id;
    private final Double montant;
    private final Date dateEmis;
    private Date DatePaye;
    private boolean paye;
}
