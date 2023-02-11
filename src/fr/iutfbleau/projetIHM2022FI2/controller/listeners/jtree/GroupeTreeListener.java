package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree;

import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;

public class GroupeTreeListener implements TreeSelectionListener{

    private DefaultListModel<Etudiant> listModel;
    
    public GroupeTreeListener(DefaultListModel<Etudiant> listModel) {
        this.listModel = listModel; 
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath  nodePath =  e.getNewLeadSelectionPath();
        DefaultMutableTreeNode node = null ;
        if(nodePath != null){
            node  = (DefaultMutableTreeNode) nodePath.getLastPathComponent();
        }
        if (node == null) return;
        Groupe groupeSelected = (Groupe) node.getUserObject();
        Set<Etudiant> etudiants = groupeSelected.getEtudiants();
        
        listModel.removeAllElements();
        for(Etudiant etu : etudiants)
            listModel.addElement(etu);
    }
}
