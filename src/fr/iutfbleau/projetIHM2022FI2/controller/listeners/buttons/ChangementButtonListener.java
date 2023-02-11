package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JOptionPane;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Changement;
import fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementButton;
import fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementPanel;
import fr.iutfbleau.projetIHM2022FI2.view.etudiant.ShowChangementPanels;

/**
 * ActionListener des boutons d'un changement
 * @see fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementPanel#addButtonsListener(ActionListener)
 */
public class ChangementButtonListener implements ActionListener {

    private ShowChangementPanels panneau;
    private AbstractChangementFactory acf;
    /**
     * Constructeur
     * @param panneau le panneau qui affiche tous les changements
     * @param acf la factory qui crée les changements
     */
    public ChangementButtonListener(ShowChangementPanels panneau, AbstractChangementFactory acf) {
        this.panneau = panneau;
        this.acf = acf;
    }

    @Override
    /**
     * Lors de l'appuie sur un changement le panneau des changements se met à jour (voir {@link fr.iutfbleau.projetIHM2022FI2.view.etudiant.ShowChangementPanels#reload() ShowChangementPanels}).
     * Si un changement ne peut être accepter 
     */
    public void actionPerformed(ActionEvent e) {
        ChangementButton bouton = (ChangementButton) e.getSource();
        if(e.getActionCommand() == ChangementPanel.ACCEPT) { 
            try {
                this.acf.applyChangement(bouton.getChangement());
            } catch (IllegalStateException e1) {
                JOptionPane.showMessageDialog(panneau, "Changement incohérent, veuillez le refuser", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (e.getActionCommand() == ChangementPanel.DENY) {
            try {
                this.acf.deleteChangement(bouton.getChangement());
            } catch (IllegalArgumentException e1) {
                JOptionPane.showMessageDialog(panneau, "Le changement n'existe plus", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.panneau.reload();
    }

    /**
     * Renvoie l'ensemble des changements de la factory
     * @return l'ensemble des {@link fr.iutfbleau.projetIHM2022FI2.API.Changement changments}
     */
    public Set<Changement> getAllChangements() {
        return this.acf.getAllChangements();
    }

}
