package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Emprunt;
import ca.cal.tp2.Modele.EmpruntDetail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public record EmpruntDTO(Long id, LocalDate emprunt, List<EmpruntDetailDTO> empruntDetails) {
    public static EmpruntDTO toDto(Emprunt emprunt) {
        List<EmpruntDetailDTO> empruntDetailDTOs = new ArrayList<>();
        for (EmpruntDetail detail : emprunt.getEmpruntDetails()) {
            empruntDetailDTOs.add(EmpruntDetailDTO.toDto(detail));
        }
        return new EmpruntDTO(emprunt.getId(), emprunt.getEmprunt(), empruntDetailDTOs);
    }
}