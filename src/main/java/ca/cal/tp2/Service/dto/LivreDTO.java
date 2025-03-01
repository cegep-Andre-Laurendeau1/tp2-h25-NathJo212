package ca.cal.tp2.Service.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LivreDTO extends DocumentDTO {
    private String auteur;
    private String editeur;
    private int nbPages;

    public LivreDTO(Long id, String titre, int anneePublication, int nombreExemplaires, String auteur, String editeur, int nbPages) {
        super(id, titre, anneePublication, nombreExemplaires);
        this.auteur = auteur;
        this.editeur = editeur;
        this.nbPages = nbPages;
    }
}