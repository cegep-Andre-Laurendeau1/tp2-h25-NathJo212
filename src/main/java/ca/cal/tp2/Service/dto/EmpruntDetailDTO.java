package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.EmpruntDetail;

import java.time.LocalDate;

public record EmpruntDetailDTO(Long lineItemId, LocalDate dateRetourPrevue, LocalDate DateRetourActuelle, String status) {
    public static EmpruntDetailDTO toDto(EmpruntDetail empruntDetail) {
        return new EmpruntDetailDTO(
                empruntDetail.getLineItemID(),
                empruntDetail.getDateRetourPrevue(),
                empruntDetail.getDateRetourActuelle(),
                empruntDetail.getStatus()
        );
    }
}
