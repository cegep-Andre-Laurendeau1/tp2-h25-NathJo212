package ca.cal.tp2.Modele;

import lombok.Data;

@Data
public abstract class Document {
    private final long id;
    private final String titre;
    private final int anneePublication;
    private final int nombreExemplaires;

    public Document(long id, String titre, int anneePublication, int nombreExemplaires){
        this.id = id;
        this.titre = titre;
        this.anneePublication = anneePublication;
        this.nombreExemplaires = nombreExemplaires;
    }
}
