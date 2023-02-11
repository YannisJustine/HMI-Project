package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.Fenetre;
import fr.iutfbleau.projetIHM2022FI2.Init;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;

/**
 * Classe représentant le listener du bouton "Confirmer" du panneau {@link fr.iutfbleau.projetIHM2022FI2.view.panel.PanelChoixEleves PanelChoixEleves}
 */
public class ChoixEleveButton implements ActionListener {

    private Fenetre fenetre;
    private CardLayout card;
    private AbstractChangementFactory acf;
    private AbstractGroupeFactory agf;
    private JList<Etudiant> list;

   /**
    * Constructeur du Listener du bouton "Confirmer" lors du choix de l'étudiant.
    * @param fenetre la classe qui contient la fenetre et le cardLayout.
    * @param agf la factory qui gère les groupes 
    * @param acf la factory qui gère les changements 
    * @param list la liste des étudiants pour avoir l'élève selectionné 
    * @see fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementPanel#addButtonsListener(ActionListener)
    */
    public ChoixEleveButton(Fenetre fenetre, AbstractGroupeFactory agf, AbstractChangementFactory acf, JList<Etudiant> list) {
        this.fenetre = fenetre;
        this.card = (CardLayout) fenetre.getContentPane().getLayout();
        this.agf = agf;
        this.acf = acf;
        this.list = list;
    }

    
    @Override
    /**
     * Lors de l'appuie sur le bouton, la page est changée avec l'étudiant choisi logger
     */
    public void actionPerformed(ActionEvent e) {
        if(list.getSelectedValue() != null) {
            Constants.loggedEtudiant = list.getSelectedValue();
            Init choix = new Init(Constants.ELEVE, agf, acf,fenetre);
            fenetre.change(choix.getTabbedPane());
            card.show(fenetre.getContentPane(),Constants.PANEL);
        }
    }
}
