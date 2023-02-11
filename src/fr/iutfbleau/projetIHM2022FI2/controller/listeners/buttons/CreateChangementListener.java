package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Changement;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;
import fr.iutfbleau.projetIHM2022FI2.view.etudiant.CreateChangementPanel;

/**
 * ActionListener du bouton "confirmer" du panneau qui crée des changements
 * @see fr.iutfbleau.projetIHM2022FI2.view.etudiant.CreateChangementPanel#setButtonListener(ActionListener)
 */
public class CreateChangementListener implements ActionListener{
    private CreateChangementPanel createChangement;
    private AbstractChangementFactory acf;
    private ReloadablePanel rp;

    /**
     * Constructeur
     * @param c le panneau dans lequel le bouton est placé
     * @param rp le panneau a mettre à jour lors de la création d'un changement
     * @param acf la factory qui crée des changements
     */
    public CreateChangementListener(CreateChangementPanel c, ReloadablePanel rp, AbstractChangementFactory acf) {
        this.createChangement = c;
        this.rp = rp;
        this.acf = acf;
    }

    @Override
    /**
     * Vérifie si un changement peut être créé puis le crée si c'est possible
     */
    public void actionPerformed(ActionEvent e) {
        try {
            Set<Changement> changements = acf.getAllChangements();
            for(Changement changement : changements) {
                if(changement.getA() == this.createChangement.getSelectedGroupeDepart() && changement.getB() == this.createChangement.getSelectedGroupeArrivee() && changement.getEtu() == Constants.loggedEtudiant) {
                    JOptionPane.showMessageDialog(this.createChangement, "Vous avez déjà demandé un changement pour les groupes sélectionnés", "Demande existante", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            this.acf.createChangement(this.createChangement.getSelectedGroupeDepart(),
                                    Constants.loggedEtudiant,
                                    this.createChangement.getSelectedGroupeArrivee(),
                                    this.createChangement.getExplications());
            this.createChangement.reset();
            this.rp.reload();
            JOptionPane.showMessageDialog(this.createChangement, "Demande envoyée", "Validation", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this.createChangement, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        
    }
}
