package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import java.awt.CardLayout;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.Fenetre;

/**
 * Classe représentant le listener du JMenuItem déconnexion.
 */
public class Deconnexion implements ActionListener{

    private JFrame fenetre;
    private CardLayout card;
    private JMenuBar bar;
    
    /**
     * Le constructeur du listener déconnexion.
     * @param fenetre la fenetre
     * @see fr.iutfbleau.projetIHM2022FI2.Fenetre
     */    
    public Deconnexion(Fenetre fenetre){
        this.fenetre = fenetre;
        this.card = (CardLayout) fenetre.getContentPane().getLayout();
        this.bar = fenetre.getJMenuBar();
    }

    @Override
    /**
     * Lors de l'appuie sur le bouton, le JMenu est retiré et on est déconnecter.
     */
    public void actionPerformed(ActionEvent e) {
        bar.remove(2);
        Constants.loggedEtudiant = null;
        card.show(this.fenetre.getContentPane(),Constants.HOME);
    }
}
