package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist;

import javax.swing.JList;
import javax.swing.JTabbedPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.view.admin.MoveStudentPanel;

/**
 * Classe représentant le PopUpMenu pour déplacer un étudiant.
 */
public class PopUpStudentButtonListener implements ActionListener{
    
    private JTabbedPane tabbedPane;
    private  JList<Etudiant> list;

    /**
     * Le constucteur du PopUpMenu.
     * @param pane le JTabbedPane pour changer de page
     * @param list la liste d'étudiant
     */
    public PopUpStudentButtonListener(JTabbedPane pane, JList<Etudiant> list){
        this.tabbedPane = pane;
        this.list = list;
    }

    @Override
    /**
     * Permet de sélectionner un élève puis changer de page.
     */
    public void actionPerformed(ActionEvent e) {
        MoveStudentPanel panel = (MoveStudentPanel) tabbedPane.getComponentAt(3);
        panel.setSelectedEtudiant(this.list.getSelectedValue());
        tabbedPane.setSelectedIndex(3);
    }
}
