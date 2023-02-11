package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JPopupMenu;

import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
/**
 * Listener d'un clic droit sur le JList qui affiche le PopUp
 * @see fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu.PopUpStudent
 */
public class PopUpStudentListener extends MouseAdapter {

    JPopupMenu pop;

    public PopUpStudentListener(JPopupMenu pop){
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
    @SuppressWarnings("unchecked")
    private void maybeShowPopup(MouseEvent e) {
        if(e.isPopupTrigger()) {
            JList<Etudiant> list = (JList<Etudiant>) e.getSource();
            int x = list.locationToIndex(e.getPoint());
            list.setSelectedIndex(x);
            list.ensureIndexIsVisible(x);
            if(list.getSelectedValue() != null)
                pop.show(e.getComponent(),e.getX(), e.getY());
        }
    }
    
}
