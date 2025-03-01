package ca.cal.tp2.Service.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class DvdDTO extends DocumentDTO {
    private String realisateur;
    private int nbMinutes;

    public DvdDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String realisateur, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.realisateur = realisateur;
        this.nbMinutes = nbMinutes;
    }
}