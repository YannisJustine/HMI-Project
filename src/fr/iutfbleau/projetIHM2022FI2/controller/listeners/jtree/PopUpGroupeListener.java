package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

/**
 * Listener d'un clic droit sur le JTree qui affiche le PopUp
 * @see fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu.PopUpGroupe
 */
public class PopUpGroupeListener extends MouseAdapter {

    JPopupMenu pop;
    /**
     * Constructeur
     * @param pop le {@link fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu.PopUpGroupe PopUpMenu} a afficher
     */
    public PopUpGroupeListener(JPopupMenu pop){
        this.pop = pop;
    }

    public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
    }

    /**
     * Fonction exécutée lors d'un clic droit
     * @param e Evenement
     * {@link <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html"> voir documentation </a>}
     */
    private void maybeShowPopup(MouseEvent e) {
        if(e.isPopupTrigger()) {
            JTree tree = (JTree) e.getSource();
            tree.setSelectionPath(tree.getClosestPathForLocation(e.getX(), e.getY()));
            pop.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}
