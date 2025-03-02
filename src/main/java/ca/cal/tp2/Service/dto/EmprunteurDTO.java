package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Amendes;
import ca.cal.tp2.Modele.Emprunt;
import ca.cal.tp2.Modele.Emprunteur;
import java.util.ArrayList;
import java.util.List;

public record EmprunteurDTO(Long id, String prenom, String nom, String email, List<EmpruntDTO> emprunts, List<AmendesDTO> amendes) {
    public static EmprunteurDTO toDto(Emprunteur emprunteur) {
        List<EmpruntDTO> empruntDTOs = new ArrayList<>();
        for (Emprunt emprunt : emprunteur.getEmprunts()) {
            empruntDTOs.add(EmpruntDTO.toDto(emprunt));
        }

        List<AmendesDTO> amendesDTOs = new ArrayList<>();
        for (Amendes amendes : emprunteur.getAmendes()) {
            amendesDTOs.add(AmendesDTO.toDto(amendes));
        }

        return new EmprunteurDTO(emprunteur.getId(), emprunteur.getPrenom(), emprunteur.getNom(), emprunteur.getEmail(), empruntDTOs, amendesDTOs);
    }
}
