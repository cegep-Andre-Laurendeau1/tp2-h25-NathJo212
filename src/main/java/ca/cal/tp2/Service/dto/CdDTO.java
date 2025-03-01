package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Cd;

public record CdDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String artiste, int nbMinutes) {
    public static CdDTO toDto(Cd cd) {
        return new CdDTO(cd.getId(), cd.getTitre(), cd.getAnneePublication(), cd.getNombreExemplaires(), cd.getArtiste(), cd.getNbMinutes());
    }
}
