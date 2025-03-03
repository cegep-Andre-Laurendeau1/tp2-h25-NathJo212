package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Cd;
import ca.cal.tp2.Modele.Document;
import ca.cal.tp2.Modele.Dvd;
import ca.cal.tp2.Modele.Livre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public abstract class DocumentDTO {
    private Long id;
    private String titre;
    private int anneePublication;
    private int nombreExemplaires;

    public static DocumentDTO toDto(Document document, int availableCount) {
        if (document instanceof Livre) {
            Livre livre = (Livre) document;
            return new LivreDTO(livre.getId(), livre.getTitre(), livre.getAnneePublication(), availableCount, livre.getAuteur(), livre.getEditeur(), livre.getNbPages());
        } else if (document instanceof Cd) {
            Cd cd = (Cd) document;
            return new CdDTO(cd.getId(), cd.getTitre(), cd.getAnneePublication(), availableCount, cd.getArtiste(), cd.getNbMinutes());
        } else if (document instanceof Dvd) {
            Dvd dvd = (Dvd) document;
            return new DvdDTO(dvd.getId(), dvd.getTitre(), dvd.getAnneePublication(), availableCount, dvd.getRealisateur(), dvd.getNbMinutes());
        }
        return null;
    }
}