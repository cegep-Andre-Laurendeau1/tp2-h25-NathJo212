package ca.cal.tp2.Service.dto;

import ca.cal.tp2.Modele.Amendes;

import java.time.LocalDate;

public record AmendesDTO(Long id, double montant, LocalDate dateEmis, LocalDate datePaye, boolean paye) {
    public static AmendesDTO toDto(Amendes amendes) {
        return new AmendesDTO( amendes.getId(), amendes.getMontant(), amendes.getDateEmis(), amendes.getDatePaye(), amendes.isPaye()
        );
    }
}