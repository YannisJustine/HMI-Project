package fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * Classe représentant un PopUpMenu pour déplacer un étudiant.
 */
public class PopUpStudent extends JPopupMenu {
    
    /**
     * constructeur du PopUpMenu.
     * @param l le listener du bouton 
     * @see fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist.PopUpStudentButtonListener Listener
     */
    public PopUpStudent(ActionListener l){
        super();
        JMenuItem move = new JMenuItem( "Déplacer cet étudiant" );
        move.addActionListener(l);
        this.add(move);
    }
}
