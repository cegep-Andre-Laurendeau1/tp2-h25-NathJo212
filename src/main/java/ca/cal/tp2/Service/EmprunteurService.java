package ca.cal.tp2.Service;

import ca.cal.tp2.Modele.*;

import java.time.LocalDate;
import java.util.*;

public class EmprunteurService {
    private final BibliothequeService bibliothequeService;

    // Variable statique pour générer l'ID d'emprunt
    private static long prochainIdEmprunt = 1;

    public EmprunteurService(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    public void emprunterDocuments(long emprunteurId, List<Long> documentIds, int annee, int mois, int jour) {
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParId(emprunteurId);
        LocalDate dateEmprunt = LocalDate.of(annee, mois, jour);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }

        for (Amendes amende : emprunteur.getAmendes()) {
            if (!amende.isPaye()) {
                throw new RuntimeException("L'emprunteur a des amendes impayées");
            }
        }

        Emprunt emprunt = new Emprunt(prochainIdEmprunt++, dateEmprunt);
        for (Long documentId : documentIds) {
            Document document = bibliothequeService.getDocumentParId(documentId);

            if (document == null) {
                throw new RuntimeException("Document non trouvé");
            }

            if (bibliothequeService.verifierQuantiteDocuments(documentId) <= 0) {
                throw new RuntimeException("Plus d'exemplaire empruntable pour le document, id: " + document.getId() + ", titre: " + document.getTitre());
            }

            LocalDate dateRetourPrevue = calculerDateRetour(document, dateEmprunt);
            EmpruntDetail empruntDetail = new EmpruntDetail(documentId, dateRetourPrevue);
            emprunt.ajouterEmpruntDetail(empruntDetail);
        }

        emprunteur.getEmprunts().add(emprunt);
    }

    public void retournerDocument(long emprunteurId, long documentId, int annee, int mois, int jour) {
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParId(emprunteurId);
        LocalDate dateRetour = LocalDate.of(annee, mois, jour);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }

        for (Emprunt emprunt : emprunteur.getEmprunts()) {
            for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                if (empruntDetail.getLineItemID() == documentId && empruntDetail.getDateRetourActuelle() == null) {
                    empruntDetail.setDateRetourActuelle(dateRetour);

                    // Calculer l'amende si le document est retourné en retard
                    if (dateRetour.isAfter(empruntDetail.getDateRetourPrevue())) {
                        long differenceEnJours = java.time.temporal.ChronoUnit.DAYS.between(empruntDetail.getDateRetourPrevue(), dateRetour);
                        double montantAmende = differenceEnJours * 0.25;

                        Amendes amende = new Amendes(System.currentTimeMillis(), montantAmende, dateRetour);
                        emprunteur.getAmendes().add(amende);
                        empruntDetail.setStatus("retourné en retard");
                    } else {
                        empruntDetail.setStatus("retourné");
                    }
                    return;
                }
            }
        }

        throw new RuntimeException("Document non trouvé dans les emprunts de l'emprunteur");
    }

    public void payerAmendes(double montant, long emprunteurId, int annee, int mois, int jour) {
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParId(emprunteurId);
        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }

        double montantRestant = montant;
        LocalDate datePaiement = LocalDate.of(annee, mois, jour);

        for (Amendes amende : emprunteur.getAmendes()) {
            if (!amende.isPaye() && montantRestant > 0) {
                if (montantRestant >= amende.getMontant()) {
                    amende.setPaye(true);
                    amende.setDatePaye(datePaiement);
                    montantRestant -= amende.getMontant();
                } else {
                    throw new RuntimeException("Montant insuffisant pour payer l'amende de " + amende.getMontant());
                }
            }
        }

        if (montantRestant > 0) {
            System.out.println("Montant restant après paiement des amendes: " + montantRestant + "\n");
        }
    }

    private LocalDate calculerDateRetour(Document document, LocalDate dateEmprunt) {
        LocalDate dateRetourPrevue = dateEmprunt;

        if (document instanceof Livre) {
            dateRetourPrevue = dateEmprunt.plusWeeks(3);
        } else if (document instanceof Cd) {
            dateRetourPrevue = dateEmprunt.plusWeeks(2);
        } else if (document instanceof Dvd) {
            dateRetourPrevue = dateEmprunt.plusWeeks(1);
        }

        return dateRetourPrevue;
    }

    public void afficherEmprunts(long emprunteurId) {
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParId(emprunteurId);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }

        for (Emprunt emprunt : emprunteur.getEmprunts()) {
            System.out.println("---------------------------------");
            System.out.println("Emprunt ID: " + emprunt.getId());
            System.out.println("Date d'emprunt: " + emprunt.getEmprunt());
            System.out.println("Documents empruntés:");
            for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                Document document = bibliothequeService.getDocumentParId(empruntDetail.getLineItemID());
                System.out.println(" - Titre: " + document.getTitre() + ", Année: " + document.getAnneePublication() + ", Date de retour prévue: " + empruntDetail.getDateRetourPrevue());
                System.out.println("   Statut: " + empruntDetail.getStatus());
                if (empruntDetail.getDateRetourActuelle() != null) {
                    System.out.println("   Date de retour actuelle: " + empruntDetail.getDateRetourActuelle());
                }
            }
            System.out.println();
        }
    }

    public void afficherInfoEmprunteur(long emprunteurId) {
        System.out.println("---------------------------------");
        Emprunteur emprunteur = bibliothequeService.getEmprunteurParId(emprunteurId);

        if (emprunteur == null) {
            throw new RuntimeException("Emprunteur non trouvé");
        }
        System.out.println("Emprunteur ID: " + emprunteur.getId());
        System.out.println("Nom: " + emprunteur.getNom());
        System.out.println("Prénom: " + emprunteur.getPrenom());
        System.out.println("Emprunts en cours:");
        for (Emprunt emprunt : emprunteur.getEmprunts()) {
            System.out.println(" - Emprunt ID: " + emprunt.getId() + ", Date d'emprunt: " + emprunt.getEmprunt());
            for (EmpruntDetail empruntDetail : emprunt.getEmpruntDetails()) {
                Document document = bibliothequeService.getDocumentParId(empruntDetail.getLineItemID());
                System.out.println("   - Titre: " + document.getTitre() + ", Date de retour prévue: " + empruntDetail.getDateRetourPrevue());
                System.out.println("     Statut: " + empruntDetail.getStatus());
                if (empruntDetail.getDateRetourActuelle() != null) {
                    System.out.println("     Date de retour actuelle: " + empruntDetail.getDateRetourActuelle());
                }
            }
        }

        System.out.println("Amendes:");
        for (Amendes amende : emprunteur.getAmendes()) {
            System.out.println(" - Montant: " + amende.getMontant() + ", Date émise: " + amende.getDateEmis());
            if (amende.isPaye()) {
                System.out.println("   Statut: Payée, Date de paiement: " + amende.getDatePaye());
            } else {
                System.out.println("   Statut: Non payée");
            }
        }
        System.out.println();
    }
}