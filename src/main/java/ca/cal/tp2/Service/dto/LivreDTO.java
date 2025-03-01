package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Livre;

public record LivreDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String auteur, String editeur, int nbPages) {
    public static LivreDTO toDto(Livre livre) {
        return new LivreDTO(livre.getId(), livre.getTitre(), livre.getAnneePublication(), livre.getNombreExemplaires(), livre.getAuteur(), livre.getEditeur(), livre.getNbPages());
    }
}
