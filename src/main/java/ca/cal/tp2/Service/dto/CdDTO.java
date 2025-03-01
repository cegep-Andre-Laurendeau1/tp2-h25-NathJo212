package ca.cal.tp2.Service.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CdDTO extends DocumentDTO {
    private String artiste;
    private int nbMinutes;

    public CdDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String artiste, int nbMinutes) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.artiste = artiste;
        this.nbMinutes = nbMinutes;
    }
}