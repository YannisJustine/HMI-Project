package fr.iutfbleau.projetIHM2022FI2.view.etudiant;

import javax.swing.JButton;
import fr.iutfbleau.projetIHM2022FI2.API.Changement;

/**
 * Un bouton qui connait son panneau
 */
public class ChangementButton extends JButton{
    private ChangementPanel parent;

    /**
     * Constructeur
     * @param string la chaine de caractère sur le bouton
     * @param vc le panneau d'un changement dans lequel le bouton est placé
     * @see fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementPanel
     */
    public ChangementButton(String string, ChangementPanel vc) {
        super(string);
        this.parent = vc;
    }

    /**
     * Permet de récupérer le changement du panneau
     * @return le changement du panneau
     */
    public Changement getChangement() {
        return this.parent.getChangement();
    }
}
