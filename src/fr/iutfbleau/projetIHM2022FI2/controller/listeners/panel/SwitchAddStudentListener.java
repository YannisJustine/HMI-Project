package fr.iutfbleau.projetIHM2022FI2.controller.listeners.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.API.TypeGroupe;
import fr.iutfbleau.projetIHM2022FI2.view.admin.AddStudentPanel;
import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;
import fr.iutfbleau.projetIHM2022FI2.view.panel.VoirGroupes;


/**
 * Classe représentant le listener du bouton pour ajouter/retirer des étudiants.
 */
public class SwitchAddStudentListener implements ActionListener {
    
    private AddStudentPanel studentPanel; 
    private VoirGroupes panel;
    private CustomJTree tree;

    /**
     * Constructeur du listener pour changer de panneau.
     * @param studentPanel le panneau permettant d'ajouter ou retirer des étudiants.
     * @param panel le panneau contenant le cardLayout 
     * @param tree l'arbre des groupes 
     */
    public SwitchAddStudentListener(AddStudentPanel studentPanel, VoirGroupes panel, CustomJTree tree){
        this.studentPanel = studentPanel;
        this.panel = panel;
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        JTree currentTree = this.tree;
        TreePath treePath = currentTree.getSelectionPath();
        if (Constants.ELEVE.equals(command) && treePath != null ){
            if(currentTree.getLastSelectedPathComponent() == this.tree.getRoot()){
                JOptionPane.showMessageDialog(studentPanel,"Vous ne pouvez pas ajouter/retirer à la racine", "Impossible d'ajouter",JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) currentTree.getLastSelectedPathComponent();
            DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) node.getParent();

            // Si le pere du groupe est une partition 
            if(((Groupe)nodeParent.getUserObject()).getType() == TypeGroupe.PARTITION){
                JOptionPane.showMessageDialog(studentPanel,"Vous ne pouvez pas ajouter/retirer au fils d'une Partition", "Impossible d'ajouter",JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Si le groupe est une partition 
            if(((Groupe)node.getUserObject()).getType() == TypeGroupe.PARTITION){
                JOptionPane.showMessageDialog(studentPanel,"Vous ne pouvez pas ajouter/retirer à une Partition", "Impossible d'ajouter",JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Si le père est vide
            if(((Groupe)nodeParent.getUserObject()).getEtudiants().isEmpty()){
                JOptionPane.showMessageDialog(studentPanel,"Vous ne pouvez pas ajouter/retirer des fils dans ce groupe car le père est vide", "Impossible d'ajouter",JOptionPane.ERROR_MESSAGE);
                return;
            }
            studentPanel.choice((Groupe)(nodeParent.getUserObject()),(Groupe)node.getUserObject());
            studentPanel.setGroupe((Groupe)node.getUserObject());
            panel.switchPanel(Constants.ELEVE);
        } 
        
        else if (Constants.HOME.equals(command)) {
            tree.setSelectionPath(null); //Obligatoire ( ça reset la selection )
            tree.setSelectionPath(treePath);
            panel.switchPanel(Constants.HOME);
        } 
   
    }   
}
