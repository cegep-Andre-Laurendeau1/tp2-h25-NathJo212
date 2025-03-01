package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Dvd;

public record DvdDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String realisateur, int nbMinutes) {
    public static DvdDTO toDto(Dvd dvd) {
        return new DvdDTO(dvd.getId(), dvd.getTitre(), dvd.getAnneePublication(), dvd.getNombreExemplaires(), dvd.getRealisateur(), dvd.getNbMinutes());
    }
}
