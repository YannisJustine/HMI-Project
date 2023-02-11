package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.Fenetre;
import fr.iutfbleau.projetIHM2022FI2.Init;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;


/**
 * Classe représentant le listener des boutons du panneau {@link fr.iutfbleau.projetIHM2022FI2.view.panel.PanelChoix PanelChoix}
 */
public class ChoixListener implements ActionListener {

    private Fenetre fenetre;
    private CardLayout card;
    private AbstractChangementFactory acf;
    private AbstractGroupeFactory agf;

    /**
     * Le constructeur du listener des boutons
     * @param fenetre la fenêtre
     * @param agf la factory des groupes
     * @param acf la factory des changements
     */
    public ChoixListener(Fenetre fenetre, AbstractGroupeFactory agf, AbstractChangementFactory acf){
        this.fenetre = fenetre;
        this.card = (CardLayout) fenetre.getContentPane().getLayout();
        this.agf = agf;
        this.acf = acf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Init choix = null;
        switch (e.getActionCommand()) {
            case Constants.ELEVE:
                card.show(fenetre.getContentPane(), Constants.ELEVE);
                return;
        
            case Constants.PROF:
                choix = new Init(Constants.PROF, agf, acf,fenetre);
                break;

            case Constants.ADMIN:
                choix = new Init(Constants.ADMIN, agf, acf,fenetre);
                break;
        }
        fenetre.change(choix.getTabbedPane());
        card.show(fenetre.getContentPane(),Constants.PANEL);
    } 
}
