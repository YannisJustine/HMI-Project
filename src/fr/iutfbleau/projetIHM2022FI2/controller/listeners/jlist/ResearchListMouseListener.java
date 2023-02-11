package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist;
import java.awt.event.MouseEvent;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.API.TypeGroupe;
import fr.iutfbleau.projetIHM2022FI2.view.custom.dialog.CustomDialogEtudiant;

@SuppressWarnings("unchecked")
public class ResearchListMouseListener extends MouseInputAdapter {

    private AbstractGroupeFactory agf = null;

    public ResearchListMouseListener(AbstractGroupeFactory agf) {
        this.agf = agf;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2) {
            if(!(e.getSource() instanceof JList))
                return;
            JList<Etudiant> list = (JList<Etudiant>) e.getSource();
            if(list.getSelectedValuesList().size() == 1) {
                //(Tester si c'est l'objet est un élève)
                Etudiant etu = (Etudiant) list.getSelectedValue();
                Set<Groupe> groupesSansPartition = new LinkedHashSet<Groupe>();
                for(Groupe g : agf.getGroupesOfEtudiant(etu)) {
                    if(g.getType() != TypeGroupe.PARTITION)
                        groupesSansPartition.add(g);
                }
                CustomDialogEtudiant d = new CustomDialogEtudiant((JFrame) SwingUtilities.getRoot(list), etu.getNom(), etu.getPrenom(), groupesSansPartition);
                d.setLocation(e.getXOnScreen() - d.getWidth() / 2, e.getYOnScreen());
                d.setVisible(true);
            }
        }
    }

}
